package com.makhalibagas.mygithubuser.data.remote.api

import com.makhalibagas.mygithubuser.data.remote.model.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getUser(
        @Query("q") query : String,
        @Query("page") page : Int
    ) : Single<ResponseSearch>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username : String
    ) : Single<Users>

    @GET("users/{username}/followers")
    fun getFollowerUser(
        @Path("username") username : String,
        @Query("page") page : Int
    ) : Single<List<Users>>


    @GET("users/{username}/following")
    fun getFollowingUser(
        @Path("username") username : String,
        @Query("page") page : Int
    ) : Single<List<Users>>

    @GET("users/{username}/repos")
    fun getRepositoryUser(
        @Path("username") username : String,
        @Query("page") page : Int
    ) : Single<List<RepositoryItem>>

}