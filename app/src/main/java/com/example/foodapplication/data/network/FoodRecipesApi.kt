package com.example.foodapplication.data.network

import com.example.foodapplication.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
/**
 * This interface defines how Retrofit talks to the web server using HTTP requests
 */
interface FoodRecipesApi {
    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>

}