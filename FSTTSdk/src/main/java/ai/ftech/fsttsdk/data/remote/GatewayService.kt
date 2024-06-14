package ai.ftech.fsttsdk.data.remote

import ai.ftech.fsttsdk.utils.AppConstant
import okhttp3.logging.HttpLoggingInterceptor

class GatewayService () : NetworkServices() {
    override var baseUrl: String = AppConstant.BASE_GATE_WAY_URL
    override val headers: Map<String, String>
        get() = mapOf(
            AppConstant.HEADER_CONTENT_TYPE to AppConstant.APPLICATION_JSON,
        )

    override var timeout: Long = AppConstant.TIMEOUT

    override var logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY

    init {
        build()
    }
}