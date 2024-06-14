package ai.ftech.fsttsdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
abstract class BaseResponse {
    @SerialName("code")
    var code: Int? = null
    @SerialName("message")
    var message: String? = null
    open fun isSuccessful(): Boolean = true
}