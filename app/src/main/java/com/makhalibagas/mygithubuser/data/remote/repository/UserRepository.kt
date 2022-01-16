package com.makhalibagas.mygithubuser.data.remote.repository

import com.makhalibagas.mygithubuser.data.remote.api.ApiService
import com.makhalibagas.mygithubuser.data.remote.model.RepositoryItem
import com.makhalibagas.mygithubuser.data.remote.model.ResponseSearch
import com.makhalibagas.mygithubuser.data.remote.model.Users
import io.reactivex.Single

class UserRepository(private val apiService: ApiService) {

    fun getUser(query: String, page: Int): Single<ResponseSearch> =
        apiService.getUser(query, page)

    fun getDetailUser(username: String): Single<Users> =
        apiService.getDetailUser(username)

    fun getFollowerUser(username: String, page: Int): Single<List<Users>> =
        apiService.getFollowerUser(username, page)

    fun getFollowingUser(username: String, page: Int): Single<List<Users>> =
        apiService.getFollowingUser(username, page)

    fun getReposUser(username: String, page: Int): Single<List<RepositoryItem>> =
        apiService.getRepositoryUser(username, page)

}