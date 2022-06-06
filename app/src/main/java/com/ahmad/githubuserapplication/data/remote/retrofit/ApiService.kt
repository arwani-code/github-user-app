package com.ahmad.githubuserapplication.data.remote.retrofit

import com.ahmad.githubuserapplication.data.remote.response.*
import retrofit2.http.*

interface ApiService {

    @GET("users")
    @Headers("Authorization: token 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")
    suspend fun getAllUsers(): List<UsersItem>

    @GET("search/users")
    @Headers("Authorization: token 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")
    suspend fun getSearchUser(
        @Query("q") username: String
    ): SearchUserResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): DetailUserResponse

    @GET("users/{username}/followers")
    suspend fun getFollowersUser(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): FollowUserResponse

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): FollowUserResponse
}