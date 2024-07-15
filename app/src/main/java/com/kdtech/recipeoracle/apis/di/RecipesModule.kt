package com.kdtech.recipeoracle.apis.di

import com.kdtech.recipeoracle.apis.data.networks.RecipesDataSourceImpl
import com.kdtech.recipeoracle.apis.data.networks.RecipesDataSource
import com.kdtech.recipeoracle.apis.data.repositories.RecipesRepositoryImpl
import com.kdtech.recipeoracle.apis.data.repositories.RecipesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RecipesDataBinds {

    @Binds
    abstract fun bindLiveStreamDataSource(
        recipesDataSourceImpl: RecipesDataSourceImpl
    ): RecipesDataSource

    @Binds
    abstract fun bindLikesOfPostRepository(
        recipesRepositoryImpl: RecipesRepositoryImpl
    ): RecipesRepository
}
