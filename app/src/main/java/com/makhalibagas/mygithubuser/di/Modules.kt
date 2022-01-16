package com.makhalibagas.mygithubuser.di

import com.makhalibagas.mygithubuser.data.remote.api.AuthInterceptor
import com.makhalibagas.mygithubuser.data.remote.api.provideApiService
import com.makhalibagas.mygithubuser.data.remote.api.provideHttpOkClient
import com.makhalibagas.mygithubuser.data.remote.repository.UserRepository
import com.makhalibagas.mygithubuser.ui.main.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AuthInterceptor(androidContext()) }
    single { provideHttpOkClient(get(), androidContext()) }
    single { provideApiService(get()) }
}

val reposModule = module {
    single { UserRepository(get()) }
}

val viewModelModule = module {
    viewModel { UserViewModel(get()) }
}

val modulesList = listOf(appModule, reposModule, viewModelModule)