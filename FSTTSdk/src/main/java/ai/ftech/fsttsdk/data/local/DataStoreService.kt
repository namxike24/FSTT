package ai.ftech.fsttsdk.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private const val PREFERENCES_NAME = "app_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreService(context: Context) : IDataStore {

    private val dataSource = context.dataStore

    override suspend fun <T> put(key: Preferences.Key<T>, value: T) {
        dataSource.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun <T> get(key: Preferences.Key<T>, defaultValue: T): T {
        val preferences = dataSource.data.first()
        return preferences[key] ?: defaultValue
    }

    override suspend fun <T> remove(key: Preferences.Key<T>) {
        dataSource.edit {
            it.remove(key)
        }
    }

    override suspend fun removeAll() {
        dataSource.edit { preferences ->
            preferences.clear()
        }
    }
}