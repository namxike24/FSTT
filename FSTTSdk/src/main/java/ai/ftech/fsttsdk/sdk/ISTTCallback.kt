package ai.ftech.fsttsdk.sdk

import ai.ftech.fsttsdk.domain.exceptions.AppException

interface ISTTCallback {
    fun onStart()
    fun onRecording()
    fun onFail(error: AppException?)
    fun onSuccess(result: String)
}
