package com.devc.watchacode.di

import com.devc.watchacode.common.Constants
import com.devc.watchacode.data.remote.ItunesApi
import com.devc.watchacode.data.repository.ItunesRepositoryImpl
import com.devc.watchacode.domain.repository.ItunesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
//Retrofit 생성자
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideItunesApi():ItunesApi{
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ItunesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideItunesRepository(api: ItunesApi): ItunesRepository {
        return ItunesRepositoryImpl(api)
    }

}