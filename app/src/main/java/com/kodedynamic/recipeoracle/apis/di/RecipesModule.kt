package com.kodedynamic.recipeoracle.apis.di

import com.kodedynamic.recipeoracle.apis.data.networks.RecipesApi
import com.kodedynamic.recipeoracle.apis.data.networks.RecipesDataSourceImpl
import com.kodedynamic.recipeoracle.apis.data.networks.RecipesDataSource
import com.kodedynamic.recipeoracle.apis.data.repositories.RecipesRepositoryImpl
import com.kodedynamic.recipeoracle.apis.data.repositories.RecipesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecipesApiBinds {
    @Provides
    @Singleton
    fun provideRecipesApi(retrofit: Retrofit): RecipesApi =
        retrofit.create(RecipesApi::class.java)
}
@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RecipesDataBinds {

    @Binds
    abstract fun bindRecipesDataSource(
        recipesDataSourceImpl: RecipesDataSourceImpl
    ): RecipesDataSource

    @Binds
    abstract fun bindRecipesRepository(
        recipesRepositoryImpl: RecipesRepositoryImpl
    ): RecipesRepository
}
