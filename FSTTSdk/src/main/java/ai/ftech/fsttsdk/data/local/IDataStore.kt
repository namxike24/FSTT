package ai.ftech.fsttsdk.data.local

import androidx.datastore.preferences.core.Preferences

interface IDataStore {
    suspend fun <T> put(key: Preferences.Key<T>, value: T)
    suspend fun <T> get(key: Preferences.Key<T>, defaultValue: T): T
    suspend fun <T> remove(key: Preferences.Key<T>)
    suspend fun removeAll()
}