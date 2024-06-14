package ai.ftech.fsttsdk.sdk

import ai.ftech.fsttsdk.R
import ai.ftech.fsttsdk.common.extension.getAppString
import ai.ftech.fsttsdk.common.extension.getString
import ai.ftech.fsttsdk.utils.FileUtils
import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioFormat
import android.media.MediaRecorder
import android.os.Build
import java.io.File
import java.io.IOException

@SuppressLint("MissingPermission")
internal class STTAudioRecorder(private val context: Context) : IAudioRecorder {
    companion object {
        const val RECORDER_SAMPLE_RATE = 16000
        const val NUMBER_CHANNELS: Int = 1
    }

    private var mRecorder: MediaRecorder? = null
    private var filePath: String? = null
    private var mListenerRecording: IAudioRecorder.IRecordingListener? = null

    override fun start() {
        mListenerRecording?.onStart()
        filePath = FileUtils.generateFileRecordPath()
        mRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.AMR_WB)
            setOutputFile(filePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB)
            setAudioSamplingRate(RECORDER_SAMPLE_RATE)
            setAudioChannels(NUMBER_CHANNELS)
            try {
                prepare()
            } catch (e: IOException) {
                e.printStackTrace()
                mListenerRecording?.onFail(e.message.getString())
            }
            start()
        }
        mListenerRecording?.onRecording()
    }

    override fun registerRecordingListener(listener: IAudioRecorder.IRecordingListener) {
        mListenerRecording = listener
    }

    override fun stop() {
        mRecorder?.apply {
            stop()
            release()
        }
        mRecorder = null

        if (filePath != null) {
            mListenerRecording?.onComplete(File(filePath!!))
        } else {
            mListenerRecording?.onFail(getAppString(R.string.message_error_record_process))
        }
    }
}
