package ai.ftech.fsttsdk.data.repositories

import ai.ftech.fsttsdk.data.source.STTDataSource
import ai.ftech.fsttsdk.domain.model.BaseCallBack
import ai.ftech.fsttsdk.domain.model.STTResponse
import ai.ftech.fsttsdk.domain.repositories.STTRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class STTRepoImpl @Inject constructor(private val sttDataSource: STTDataSource) : STTRepo {
    override fun stt(filePath: String): Flow<BaseCallBack<STTResponse>> {
        return sttDataSource.stt(filePath)
    }
}