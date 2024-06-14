package ai.ftech.fsttsdk.data.repositories

import ai.ftech.fsttsdk.domain.model.STTResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ISTTAPI {
    @Multipart
    @POST("stt/transform")
    suspend fun stt(@Part file: MultipartBody.Part): STTResponse
}