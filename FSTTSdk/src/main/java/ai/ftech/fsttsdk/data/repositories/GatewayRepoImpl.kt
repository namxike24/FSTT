package ai.ftech.fsttsdk.data.repositories

import ai.ftech.fsttsdk.data.source.GatewayDataSource
import ai.ftech.fsttsdk.domain.model.BaseCallBack
import ai.ftech.fsttsdk.domain.model.InitGatewayRequest
import ai.ftech.fsttsdk.domain.model.InitGatewayResponse
import ai.ftech.fsttsdk.domain.repositories.GatewayRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GatewayRepoImpl @Inject constructor(private val gatewayDataSource: GatewayDataSource) : GatewayRepo {
    override fun initGateway(request: InitGatewayRequest): Flow<BaseCallBack<InitGatewayResponse>> {
        return gatewayDataSource.initGateway(request)
    }

}