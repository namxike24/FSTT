package ai.ftech.fsttsdk.data.source

import ai.ftech.fsttsdk.domain.model.BaseCallBack
import ai.ftech.fsttsdk.domain.model.InitGatewayRequest
import ai.ftech.fsttsdk.domain.model.InitGatewayResponse
import ai.ftech.fsttsdk.domain.model.STTResponse
import kotlinx.coroutines.flow.Flow

interface STTDataSource {
    fun stt(filePath: String): Flow<BaseCallBack<STTResponse>>
}