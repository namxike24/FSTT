package ai.ftech.fsttsdk.domain.exceptions

import ai.ftech.fsttsdk.utils.AppConstant

data class AppException(
    override var message: String? = null,
    var statusCode: Int = AppConstant.UNKNOWN_ERROR,
): Exception()