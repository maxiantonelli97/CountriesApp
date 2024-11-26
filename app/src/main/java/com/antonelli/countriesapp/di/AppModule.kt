package com.antonelli.countriesapp.di

import com.antonelli.countriesapp.api.ApiService
import com.antonelli.countriesapp.BuildConfig
import com.antonelli.countriesapp.domain.GetAllCountriesUseCase
import com.antonelli.countriesapp.domain.SearchUseCase
import com.antonelli.countriesapp.repository.MainRepository
import com.antonelli.countriesapp.repository.MainRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl() = BuildConfig.BASE_URL.toHttpUrl()

    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl: HttpUrl): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun providesMainRepo(
        remoteService: ApiService,
    ): MainRepository {
        return MainRepositoryImp(remoteService)
    }

    @Provides
    fun providesSearchUseCase(
        mainRepository: MainRepository,
        dispatcher: CoroutineDispatcher
    ): SearchUseCase {
        return SearchUseCase(mainRepository, dispatcher)
    }

    fun providesGetAllCountriesUseCase(
        mainRepository: MainRepository,
        dispatcher: CoroutineDispatcher
    ): GetAllCountriesUseCase {
        return GetAllCountriesUseCase(mainRepository, dispatcher)
    }

    @Provides
    @Singleton
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}