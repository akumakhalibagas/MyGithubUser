package com.makhalibagas.mygithubuser.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.makhalibagas.mygithubuser.data.remote.api.Status
import com.makhalibagas.mygithubuser.data.remote.model.RepositoryItem
import com.makhalibagas.mygithubuser.data.remote.model.ResponseSearch
import com.makhalibagas.mygithubuser.data.remote.model.Users
import com.makhalibagas.mygithubuser.data.remote.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val compositeDispose = CompositeDisposable()

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
        compositeDispose.add(
            userRepository.getUser(query, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    users.value = Status.Success(it)
                }, {
                    users.value = Status.Error(it)
                })
        )
    }

    fun getDetailUser(username: String) {
        usersDetail.value = Status.Loading()
        compositeDispose.add(
            userRepository.getDetailUser(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    usersDetail.value = Status.Success(it)
                }, {
                    usersDetail.value = Status.Error(it)
                })
        )
    }

    fun getFollowersUser(username: String, page: Int) {
        followers.value = Status.Loading()
        compositeDispose.add(
            userRepository.getFollowerUser(username, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    followers.value = Status.Success(it)
                }, {
                    followers.value = Status.Error(it)
                })
        )
    }

    fun getFollowingUser(username: String, page: Int) {
        following.value = Status.Loading()
        compositeDispose.add(
            userRepository.getFollowingUser(username, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    following.value = Status.Success(it)
                }, {
                    following.value = Status.Error(it)
                })
        )
    }

    fun getReposUser(username: String, page: Int) {
        repos.value = Status.Loading()
        compositeDispose.add(
            userRepository.getReposUser(username, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    repos.value = Status.Success(it)
                }, {
                    repos.value = Status.Error(it)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDispose.dispose()
    }
}