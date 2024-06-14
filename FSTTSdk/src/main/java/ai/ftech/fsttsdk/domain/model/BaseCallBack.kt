package ai.ftech.fsttsdk.domain.model

import ai.ftech.fsttsdk.domain.exceptions.AppException

sealed class BaseCallBack<T>(val error: AppException? = null,
                             val data: T? = null) {
    class Success<T>(data: T?) : BaseCallBack<T>(data = data)
    class Error<T>(error: AppException? = null) : BaseCallBack<T>(error = error)
}

