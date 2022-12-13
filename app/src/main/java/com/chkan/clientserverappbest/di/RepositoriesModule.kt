package com.chkan.clientserverappbest.di

import com.chkan.clientserverappbest.data.MainRepositoryImpl
import com.chkan.clientserverappbest.data.repos.SearchRepositoryImpl
import com.chkan.clientserverappbest.domain.MainRepository
import com.chkan.clientserverappbest.domain.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Dmytro Chkan on 01.12.2022.
 */
@InstallIn(SingletonComponent::class)
@Module
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideMainRepository(repository: MainRepositoryImpl): MainRepository = repository

    @Provides
    @Singleton
    fun provideSearchRepository(repository: SearchRepositoryImpl): SearchRepository = repository
}