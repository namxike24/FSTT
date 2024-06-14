package ai.ftech.fsttsdk.data.repositories

import ai.ftech.fsttsdk.domain.model.InitGatewayRequest
import ai.ftech.fsttsdk.domain.model.InitGatewayResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface IGatewayAPI {
    @POST("auth/sdk/init")
    suspend fun initGateway(@Body request: InitGatewayRequest): InitGatewayResponse
}