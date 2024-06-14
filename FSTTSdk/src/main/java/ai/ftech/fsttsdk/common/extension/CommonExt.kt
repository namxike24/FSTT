package ai.ftech.fsttsdk.common.extension

import ai.ftech.fsttsdk.sdk.STTManager
import android.app.Application
import android.content.Context
import androidx.annotation.StringRes

private var application: Application? = null

fun getApplication() = application

fun setApplication(context: Application) {
    application = context
}

fun String?.getString(defaultValue: String = ""): String {
    return if(this.isNullOrEmpty()) defaultValue else this
}

fun getAppString(
    @StringRes stringId: Int,
    context: Context? = STTManager.getApplicationContext()
): String {
    return context?.getString(stringId) ?: ""
}