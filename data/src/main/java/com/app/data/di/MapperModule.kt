package com.app.data.di

import com.app.data.localsource.mappers.LocalVideoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun provideLocalVideoMapper(): LocalVideoMapper {
        return LocalVideoMapper()
    }
}