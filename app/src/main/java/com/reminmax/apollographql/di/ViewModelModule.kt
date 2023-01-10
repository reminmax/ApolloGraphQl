package com.reminmax.apollographql.di

import com.reminmax.apollographql.data.repository.CharacterRepository
import com.reminmax.apollographql.data.repository.ICharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRepository(repositoryImpl: CharacterRepository): ICharacterRepository

}