package com.kdtech.recipeoracle.apis.data.networks

import com.kdtech.recipeoracle.apis.data.models.CategoriesDto
import com.kdtech.recipeoracle.apis.data.models.HomeFeedWidgetsDto
import com.kdtech.recipeoracle.apis.data.models.RecipeListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesApi {
    @GET("/get_widgets")
    suspend fun getHomeFeedData(): Response<HomeFeedWidgetsDto>

    @GET("/get_cuisines")
    suspend fun getCategories(): Response<CategoriesDto>

    @GET("/see_all_recipes")
    suspend fun getSeeAllRecipes(
        @Query("cuisine_type") cuisineType: String?,
        @Query("prep_time") prepTime: Int?,
        @Query("health_rating") healthRating: Int?,
        @Query("top_rated") topRated: Boolean?
    ): Response<RecipeListDto>
}