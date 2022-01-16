package com.makhalibagas.mygithubuser.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makhalibagas.mygithubuser.data.remote.api.Status
import com.makhalibagas.mygithubuser.data.remote.model.*
import com.makhalibagas.mygithubuser.data.remote.repository.UserRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    val users by lazy {
        MutableLiveData<Status<ResponseSearch>>()
    }

    val followers by lazy {
        MutableLiveData<Status<List<Users>>>()
    }

    val following by lazy {
        MutableLiveData<Status<List<Users>>>()
    }

    val repos by lazy {
        MutableLiveData<Status<List<RepositoryItem>>>()
    }

    val usersDetail by lazy {
        MutableLiveData<Status<Users>>()
    }



    fun getUser(query: String, page: Int) {
        users.value = Status.Loading()
        viewModelScope.launch {
            userRepository.getUser(query, page)
                .catch {
                    users.value = Status.Error(it)
                }
                .collect {
                    users.value = Status.Success(it)
                }
        }
    }

    fun getDetailUser(username: String){
        usersDetail.value = Status.Loading()
        viewModelScope.launch {
            userRepository.getDetailUser(username)
                .catch {
                    usersDetail.value = Status.Error(it)
                }
                .collect{
                    usersDetail.value = Status.Success(it)
                }
        }
    }

    fun getFollowersUser(username: String, page: Int){
        followers.value = Status.Loading()
        viewModelScope.launch {
            userRepository.getFollowerUser(username, page)
                .catch {
                    followers.value = Status.Error(it)
                }
                .collect{
                    followers.value = Status.Success(it)
                }
        }
    }

    fun getFollowingUser(username: String, page: Int){
        following.value = Status.Loading()
        viewModelScope.launch {
            userRepository.getFollowingUser(username, page)
                .catch {
                    following.value = Status.Error(it)
                }
                .collect{
                    following.value = Status.Success(it)
                }
        }
    }

    fun getReposUser(username: String, page: Int){
        repos.value = Status.Loading()
        viewModelScope.launch {
            userRepository.getReposUser(username, page)
                .catch {
                    repos.value = Status.Error(it)
                }
                .collect{
                    repos.value = Status.Success(it)
                }
        }
    }
}