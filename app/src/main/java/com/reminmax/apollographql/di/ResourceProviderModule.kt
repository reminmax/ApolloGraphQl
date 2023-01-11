package com.reminmax.apollographql.di

import android.content.Context
import com.reminmax.apollographql.ui.helpers.resource_provider.AndroidIResourceProvider
import com.reminmax.apollographql.ui.helpers.resource_provider.IResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ResourceProviderModule {

    @Provides
    @Singleton
    fun provideAndroidResourceProvider(@ApplicationContext context: Context): IResourceProvider {
        return AndroidIResourceProvider(context)
    }
}