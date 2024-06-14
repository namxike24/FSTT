package ai.ftech.fsttsdk.data.remote

import ai.ftech.fsttsdk.data.local.IDataStore
import ai.ftech.fsttsdk.utils.AppConstant
import ai.ftech.fsttsdk.utils.DataStoreConstant
import kotlinx.coroutines.runBlocking
import okhttp3.logging.HttpLoggingInterceptor

class STTService (private val datastore: IDataStore) : NetworkServices() {
    override var baseUrl: String = AppConstant.BASE_STT_URL
    override val headers: Map<String, String>
        get() = mapOf(
            AppConstant.HEADER_CONTENT_TYPE to AppConstant.APPLICATION_JSON,
            AppConstant.HEADER_AUTHORIZATION to runBlocking {
                String.format(AppConstant.AUTH_BEARER, datastore.get(DataStoreConstant.TOKEN_KEY, ""))
            }
        )

    override var timeout: Long = AppConstant.TIMEOUT

    override var logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY

    init {
        build()
    }
}