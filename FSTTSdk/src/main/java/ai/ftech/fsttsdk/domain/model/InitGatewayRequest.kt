package ai.ftech.fsttsdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InitGatewayRequest(
    @SerialName("app_id")
    var appId: String? = null,
    @SerialName("secret_key")
    var secretKey: String? = null
)