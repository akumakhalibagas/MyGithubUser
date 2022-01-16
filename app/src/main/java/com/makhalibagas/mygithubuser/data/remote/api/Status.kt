package com.makhalibagas.mygithubuser.data.remote.api

sealed class Status<T> {
    data class Loading<T>(val isLoading: Boolean = true) : Status<T>()
    data class Success<T>(val data : T) : Status<T>()
    data class Error<T>(val throwable: Throwable) : Status<T>()
}