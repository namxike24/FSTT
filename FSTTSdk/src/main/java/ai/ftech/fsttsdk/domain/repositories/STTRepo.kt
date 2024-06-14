package ai.ftech.fsttsdk.domain.repositories

import ai.ftech.fsttsdk.domain.model.BaseCallBack
import ai.ftech.fsttsdk.domain.model.InitGatewayRequest
import ai.ftech.fsttsdk.domain.model.InitGatewayResponse
import ai.ftech.fsttsdk.domain.model.STTResponse
import kotlinx.coroutines.flow.Flow

interface STTRepo {
    fun stt(filePath: String): Flow<BaseCallBack<STTResponse>>
}