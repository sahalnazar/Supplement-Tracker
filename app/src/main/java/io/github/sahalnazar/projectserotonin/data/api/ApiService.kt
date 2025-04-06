package io.github.sahalnazar.projectserotonin.data.api

import io.github.sahalnazar.projectserotonin.data.model.remote.MainResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/v92/users/{userId}/programs/items_to_consume")
    suspend fun getItemsToConsume(
        @Path("userId") userId: String,
        @Query("withoutAuth") withoutAuth: Boolean = true,
        @Query("userId") queryUserId: String = userId
    ): MainResponse

    @POST("api/v92/users/{userId}/supplements/{id}/{date}/{time}")
    suspend fun addConsumption(
        @Path("userId") userId: String,
        @Path("id") id: String,
        @Path("date") date: String,
        @Path("time") time: String,
        @Query("withoutAuth") withoutAuth: Boolean = true,
        @Query("userId") queryUserId: String = userId
    )

    @DELETE("api/v92/users/{userId}/supplements/{id}/{date}/{time}")
    suspend fun deleteConsumption(
        @Path("userId") userId: String,
        @Path("id") id: String,
        @Path("date") date: String,
        @Path("time") time: String,
        @Query("withoutAuth") withoutAuth: Boolean = true,
        @Query("userId") queryUserId: String = userId
    )
}
