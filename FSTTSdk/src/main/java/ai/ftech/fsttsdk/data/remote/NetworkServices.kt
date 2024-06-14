package ai.ftech.fsttsdk.data.remote

import ai.ftech.fsttsdk.utils.AppConstant
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.Headers.Companion.toHeaders
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

private val json = Json {
    ignoreUnknownKeys = true
}

abstract class NetworkServices {
    private lateinit var retrofit: Retrofit

    abstract var baseUrl: String

    abstract val headers: Map<String, String>

    abstract var timeout: Long

    abstract var logLevel: HttpLoggingInterceptor.Level

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    fun build() {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory(AppConstant.APPLICATION_JSON.toMediaType()))
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                    .readTimeout(timeout, TimeUnit.MILLISECONDS)
                    .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                    .addInterceptor(Interceptor { chain ->
                        val request = chain.request().newBuilder()
                            .headers(headers.toHeaders())
                            .build()
                        chain.proceed(request)
                    })
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = logLevel
                    })
                    .build()
            )
            .build()
    }
}

