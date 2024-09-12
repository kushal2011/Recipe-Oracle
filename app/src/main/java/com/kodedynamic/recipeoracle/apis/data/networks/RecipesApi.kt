package com.kodedynamic.recipeoracle.apis.data.networks

import com.google.gson.JsonObject
import com.kodedynamic.recipeoracle.apis.data.models.CategoriesDto
import com.kodedynamic.recipeoracle.apis.data.models.HomeFeedWidgetsDto
import com.kodedynamic.recipeoracle.apis.data.models.OpenAiChatDto
import com.kodedynamic.recipeoracle.apis.data.models.OpenAiChatRequestDto
import com.kodedynamic.recipeoracle.apis.data.models.RecipeDto
import com.kodedynamic.recipeoracle.apis.data.models.RecipeListDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface RecipesApi {
    @GET("/get_widgets")
    suspend fun getHomeFeedData(): Response<HomeFeedWidgetsDto>

    @GET("/get_cuisines")
    suspend fun getCategories(): Response<CategoriesDto>

    @GET("/recipes/{recipe_id}")
    suspend fun getRecipeById(
        @Path("recipe_id") recipeId: String
    ): Response<RecipeDto>

    @GET("/see_all_recipes")
    suspend fun getSeeAllRecipes(
        @Query("cuisine_type") cuisineType: String?,
        @Query("prep_time") prepTime: Int?,
        @Query("health_rating") healthRating: Int?,
        @Query("top_rated") topRated: Boolean?
    ): Response<RecipeListDto>

    @GET("/search")
    suspend fun searchRecipes(
        @Query("search_text") searchText: String,
    ): Response<RecipeListDto>

    @POST("/recipes/bulk/")
    suspend fun postGeneratedRecipes(
        @Body body: JsonObject
    ): Response<Unit>

    @POST
    suspend fun openAiChatApi(
        @Url url: String,
        @Body body: OpenAiChatRequestDto
    ): Response<OpenAiChatDto>
}