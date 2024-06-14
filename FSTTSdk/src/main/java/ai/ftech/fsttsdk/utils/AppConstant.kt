package ai.ftech.fsttsdk.utils

object AppConstant {
    const val BASE_GATE_WAY_URL = "https://fcloud-api-gateway.dev.ftech.ai/"
    const val BASE_STT_URL = "https://fcloud-stt.dev.ftech.ai/"
    const val TIMEOUT = 30000L
    const val HEADER_ACCEPT = "accept"
    const val HEADER_CONTENT_TYPE = "content-type"
    const val HEADER_AUTHORIZATION = "Authorization"
    const val APPLICATION_JSON = "application/json"

    const val PAGE_LIMIT = 20

    const val UNKNOWN_ERROR = -20001
    const val TIME_OUT_ERROR = -20002
    const val PAGING_API_ERROR = -20003
    const val AUTH_BEARER = "Bearer %s"
    const val TOKEN_EXPIRED_ERROR = 401
}