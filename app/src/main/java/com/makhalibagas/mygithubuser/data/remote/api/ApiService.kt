package com.makhalibagas.mygithubuser.data.remote.api

import com.makhalibagas.mygithubuser.data.remote.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun getUser(
        @Query("q") query : String,
        @Query("page") page : Int
    ) : ResponseSearch

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username : String
    ) : Users

    @GET("users/{username}/followers")
    suspend fun getFollowerUser(
        @Path("username") username : String,
        @Query("page") page : Int
    ) : List<Users>


    @GET("users/{username}/following")
    suspend fun getFollowingUser(
        @Path("username") username : String,
        @Query("page") page : Int
    ) : List<Users>

    @GET("users/{username}/repos")
    suspend fun getRepositoryUser(
        @Path("username") username : String,
        @Query("page") page : Int
    ) : List<RepositoryItem>


}