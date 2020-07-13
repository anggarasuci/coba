package com.example.coba.di

import com.example.coba.data.remote.UserRemoteDataSource
import com.example.coba.data.repository.UserRepository
import com.example.coba.domain.user.GetUserSearchUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetUserSearchUseCase(userRepository = get()) }
}

val repositoryModule = module {
    single { UserRepository(userRemoteDataSource = get()) }
}

val dataSourceModule = module {
    single { UserRemoteDataSource(userService = get()) }
}

val otherModule = module {}

val domainDataModule = listOf(useCaseModule, repositoryModule, dataSourceModule, otherModule)
