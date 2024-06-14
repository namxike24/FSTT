package ai.ftech.fsttsdk.common.extension

import androidx.datastore.preferences.core.Preferences
import ai.ftech.fsttsdk.data.local.IDataStore
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

suspend inline fun <reified T> IDataStore.putModel(key: Preferences.Key<String>, value: T) {
    put(key, Json.encodeToString(value))
}

suspend inline fun <reified T> IDataStore.getModel(key: Preferences.Key<String>): T? {
    val string = get(key, "")
    try {
        return if (string.isEmpty()) {
            null
        } else {
            Json.decodeFromString(string)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}