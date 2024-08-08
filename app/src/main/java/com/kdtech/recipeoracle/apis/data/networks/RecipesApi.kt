package com.kdtech.recipeoracle.apis.data.networks

import com.kdtech.recipeoracle.apis.data.models.HomeFeedWidgetsDto
import retrofit2.Response
import retrofit2.http.GET

interface RecipesApi {
    @GET("/get_widgets")
    suspend fun getHomeFeedData(): Response<HomeFeedWidgetsDto>
}