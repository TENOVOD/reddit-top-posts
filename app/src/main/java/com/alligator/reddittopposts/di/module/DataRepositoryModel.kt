package com.alligator.reddittopposts.di.module

import com.alligator.reddittopposts.data.remote.api.RedditApiService
import com.alligator.reddittopposts.data.repository.impl.DataRepositoryImpl
import com.alligator.reddittopposts.data.repository.interfaces.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataRepositoryModel {

    @Provides
    fun provideDataRepository(
        apiService: RedditApiService
    ):DataRepository = DataRepositoryImpl(apiService)

}