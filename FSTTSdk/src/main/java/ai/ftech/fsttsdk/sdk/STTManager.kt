package ai.ftech.fsttsdk.sdk

import ai.ftech.fsttsdk.R
import ai.ftech.fsttsdk.common.extension.getAppString
import ai.ftech.fsttsdk.common.extension.getString
import ai.ftech.fsttsdk.common.extension.setApplication
import ai.ftech.fsttsdk.data.local.DataStoreService
import ai.ftech.fsttsdk.data.local.IDataStore
import ai.ftech.fsttsdk.data.remote.GatewayService
import ai.ftech.fsttsdk.data.remote.STTService
import ai.ftech.fsttsdk.data.repositories.IGatewayAPI
import ai.ftech.fsttsdk.data.repositories.ISTTAPI
import ai.ftech.fsttsdk.data.source.remote.GatewayRemoteDataSource
import ai.ftech.fsttsdk.data.source.remote.STTRemoteDataSource
import ai.ftech.fsttsdk.domain.exceptions.AppException
import ai.ftech.fsttsdk.domain.model.BaseCallBack
import ai.ftech.fsttsdk.domain.model.InitGatewayRequest
import ai.ftech.fsttsdk.utils.AppConstant
import ai.ftech.fsttsdk.utils.DataStoreConstant
import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

object STTManager {
    private val TAG: String = STTManager::class.java.simpleName
    private var applicationContext: Context? = null
    private var gateWayRemote: GatewayRemoteDataSource? = null
    private var sttRemote: STTRemoteDataSource? = null
    private var dataStore: IDataStore? = null
    private var sttCallback: ISTTCallback? = null
    private var audioRecorder: IAudioRecorder? = null

    @JvmStatic
    fun init(context: Context) {
        applicationContext = context
        setApplication(getApplicationContext())
        dataStore = DataStoreService(context)
        gateWayRemote = GatewayRemoteDataSource(
            context,
            GatewayService().create(IGatewayAPI::class.java))
        sttRemote = STTRemoteDataSource(context, STTService(dataStore as IDataStore).create(ISTTAPI::class.java))
        audioRecorder = STTAudioRecorder(context)
        runBlocking {
            dataStore?.remove(DataStoreConstant.TOKEN_KEY)
        }
    }

    fun getApplicationContext(): Application {
        return applicationContext as? Application
            ?: throw RuntimeException(getAppString(R.string.context_null_error))
    }

    @JvmStatic
    fun initGateway(appId: String, secretKey: String, callback: IInitGatewayCallback) {
        if (appId.isEmpty()) {
            callback.onFail(
                AppException(
                    statusCode = AppConstant.UNKNOWN_ERROR,
                    message = getAppString(R.string.empty_app_id)
                )
            )
            return
        }
        if (secretKey.isEmpty()) {
            callback.onFail(
                AppException(
                    statusCode = AppConstant.UNKNOWN_ERROR,
                    message = getAppString(R.string.empty_secret_key)
                )
            )
            return
        }
        val request = InitGatewayRequest().apply {
            this.appId = appId
            this.secretKey = secretKey
        }
        gateWayRemote?.let { remote ->
            CoroutineScope(Dispatchers.IO).launch {
                remote.initGateway(request).collect { result ->
                    when (result) {
                        is BaseCallBack.Success -> {
                            val token = result.data?.data?.token.getString()
                            if (token.isEmpty()) {
                                callback.onFail(AppException(getAppString(R.string.message_token_is_empty)))
                            } else {
                                dataStore?.put(DataStoreConstant.TOKEN_KEY, result.data?.data?.token.getString())
                                callback.onSuccess()
                            }
                        }

                        is BaseCallBack.Error -> {
                            callback.onFail(result.error)
                        }
                    }
                }
            }
        }
    }

    @JvmStatic
    fun registerSTTCallback(sttCallback: ISTTCallback) {
        this.sttCallback = sttCallback
    }

    @JvmStatic
    fun startSTT() {
        if (!checkRecordAudioPermission()) {
            sttCallback?.onFail(AppException(getAppString(R.string.message_permission_audio_denied)))
            return
        }

        if (audioRecorder == null) {
            sttCallback?.onFail(AppException(getAppString(R.string.message_sdk_not_initial)))
            return
        }

        if (isNotInitGateway()) {
            sttCallback?.onFail(AppException(getAppString(R.string.message_recorder_not_initial)))
            return
        }

        audioRecorder?.registerRecordingListener(object : IAudioRecorder.IRecordingListener {
            override fun onStart() {
                sttCallback?.onStart()
            }

            override fun onRecording() {
                sttCallback?.onRecording()
            }

            override fun onComplete(fileRecord: File) {
                processSTT(fileRecord.absolutePath)
            }

            override fun onFail(reason: String) {
                sttCallback?.onFail(AppException(reason))
            }
        })

        audioRecorder?.start()
    }

    @JvmStatic
    fun stopSTT() {
        if (audioRecorder == null) {
            sttCallback?.onFail(AppException(getAppString(R.string.message_sdk_not_initial)))
            return
        }

        if (isNotInitGateway()) {
            sttCallback?.onFail(AppException(getAppString(R.string.message_recorder_not_initial)))
            return
        }

        audioRecorder?.stop()
    }

    private fun processSTT(filePath: String) {
        sttRemote?.let { remote ->
            CoroutineScope(Dispatchers.IO).launch {
                remote.stt(filePath).collect { result ->
                    when (result) {
                        is BaseCallBack.Success -> {
                            sttCallback?.onSuccess(result.data?.data?.transformData?.transcript.getString())
                        }

                        is BaseCallBack.Error -> {
                            sttCallback?.onFail(result.error)
                        }
                    }
                }
            }
        }
    }

    private fun checkRecordAudioPermission(): Boolean = ActivityCompat.checkSelfPermission(
        getApplicationContext(), Manifest.permission.RECORD_AUDIO
    ) == PackageManager.PERMISSION_GRANTED

    private fun isNotInitGateway(): Boolean {
        return runBlocking {
            val token = dataStore?.get(DataStoreConstant.TOKEN_KEY, "")?.getString()
            token.isNullOrEmpty()
        }
    }
}