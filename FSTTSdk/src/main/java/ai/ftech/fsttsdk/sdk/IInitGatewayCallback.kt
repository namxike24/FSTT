package ai.ftech.fsttsdk.sdk

import ai.ftech.fsttsdk.domain.exceptions.AppException

interface IInitGatewayCallback {
    fun onSuccess() {
    }

    fun onFail(error: AppException?) {
    }

}