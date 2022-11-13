package com.coucouapp.di

import android.content.Context
import com.coucouapp.data.remote.RestApis
import com.coucouapp.data.storagehelper.RemoteHelper
import com.coucouapp.database.datastore.AdarshIndiaDataStore
import com.example.adarshindia.data.repository.AdarshIndiaRepository
import com.rwc.data.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideAuthApi(
        remoteDataSource: RetrofitClient, @ApplicationContext context: Context
    ): RestApis {
        return remoteDataSource.buildApi(RestApis::class.java, context)
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideRawRepository(@ApplicationContext context: Context, remoteHelper: RemoteHelper, adarshIndiaDataStore: AdarshIndiaDataStore): AdarshIndiaRepository {
        return AdarshIndiaRepository(context, remoteHelper, adarshIndiaDataStore)
    }
}


