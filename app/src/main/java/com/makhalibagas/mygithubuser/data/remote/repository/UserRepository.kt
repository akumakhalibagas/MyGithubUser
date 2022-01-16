package com.makhalibagas.mygithubuser.data.remote.repository

import com.makhalibagas.mygithubuser.data.remote.api.ApiService
import com.makhalibagas.mygithubuser.data.remote.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(private val apiService: ApiService) {

    suspend fun getUser(query: String, page: Int): Flow<ResponseSearch> {
        return flow {
            val result = apiService.getUser(query, page)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailUser(username: String) : Flow<Users>{
        return flow {
            val result = apiService.getDetailUser(username)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollowerUser(username: String, page:Int) : Flow<List<Users>>{
        return flow {
            val result = apiService.getFollowerUser(username, page)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollowingUser(username: String,page: Int) : Flow<List<Users>>{
        return flow {
            val result = apiService.getFollowingUser(username, page)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getReposUser(username: String, page: Int) : Flow<List<RepositoryItem>>{
        return flow {
            val result = apiService.getRepositoryUser(username, page)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}