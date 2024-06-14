package ai.ftech.fsttsdk.data.source

import ai.ftech.fsttsdk.domain.model.BaseCallBack
import ai.ftech.fsttsdk.domain.model.InitGatewayRequest
import ai.ftech.fsttsdk.domain.model.InitGatewayResponse
import kotlinx.coroutines.flow.Flow

interface GatewayDataSource {
    fun initGateway(request: InitGatewayRequest): Flow<BaseCallBack<InitGatewayResponse>>
}