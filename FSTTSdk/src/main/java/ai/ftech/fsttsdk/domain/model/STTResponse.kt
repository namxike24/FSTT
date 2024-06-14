package ai.ftech.fsttsdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class STTResponse(
    @SerialName("data")
    var data: STTData? = null
) : BaseResponse() {
    override fun isSuccessful(): Boolean {
        return code == 0 && data != null
    }
}