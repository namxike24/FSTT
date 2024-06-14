package ai.ftech.fsttsdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class STTData(
    @SerialName("audio_url")
    var audioUrl: String? = null,
    @SerialName("transform_data")
    var transformData: STTTransformData? = null
)

@Serializable
data class STTTransformData(
    @SerialName("transcript")
    var transcript: String? = null
)