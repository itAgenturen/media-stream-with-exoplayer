package com.app.data.di

import android.content.Context
import android.content.res.AssetManager
import com.app.data.localsource.mappers.LocalVideoMapper
import com.app.data.localsource.repository.VideosRepositoryImpl
import com.app.domain.repositories.IVideosRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideVideosRepository(
        assetManager: AssetManager,
        loginRespMapper: LocalVideoMapper,
        moshi: Moshi
    ): IVideosRepository {
        return VideosRepositoryImpl(assetManager, loginRespMapper, moshi)
    }

    @Provides
    @Singleton
    fun provideAssetManager(@ApplicationContext context: Context): AssetManager {
        return context.assets
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }
}