package ai.ftech.fsttsdk.di

import android.content.Context
import ai.ftech.fsttsdk.data.local.DataStoreService
import ai.ftech.fsttsdk.data.local.IDataStore
import ai.ftech.fsttsdk.data.remote.GatewayService
import ai.ftech.fsttsdk.data.remote.STTService
import ai.ftech.fsttsdk.data.repositories.IGatewayAPI
import ai.ftech.fsttsdk.data.repositories.ISTTAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGatewayApi(): IGatewayAPI {
        return GatewayService().create(IGatewayAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideSTTApi(datastore: IDataStore): ISTTAPI {
        return STTService(datastore).create(ISTTAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext app: Context
    ): IDataStore = DataStoreService(app)

}