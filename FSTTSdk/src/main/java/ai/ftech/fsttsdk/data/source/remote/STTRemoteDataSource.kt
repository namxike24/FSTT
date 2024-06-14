package ai.ftech.fsttsdk.data.source.remote

import ai.ftech.fsttsdk.data.repositories.ISTTAPI
import ai.ftech.fsttsdk.data.source.STTDataSource
import ai.ftech.fsttsdk.data.source.handle
import ai.ftech.fsttsdk.domain.model.BaseCallBack
import ai.ftech.fsttsdk.domain.model.STTResponse
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class STTRemoteDataSource @Inject constructor(
    @ApplicationContext val context: Context,
    private val sttAPI: ISTTAPI,
) : STTDataSource {

    companion object {
        private const val PART_FILE = "file"
    }

    override fun stt(filePath: String): Flow<BaseCallBack<STTResponse>> {
        return flow {
            val part = convertFileToMultipart(filePath)
            val result = sttAPI.runCatching { stt(part) }
            emit(result.handle())
        }
    }

    private fun convertFileToMultipart(absolutePath: String): MultipartBody.Part {
        val file = File(absolutePath)
        return MultipartBody.Part.createFormData(PART_FILE, file.name, file.asRequestBody())
    }
}