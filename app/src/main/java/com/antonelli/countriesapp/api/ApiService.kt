package com.antonelli.countriesapp.api

import com.antonelli.countriesapp.entity.CountryModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("all")
    suspend fun getAllCountries(): Response<ArrayList<CountryModel>>?

    @GET("name/{name}")
    suspend fun searchCountry(@Path("name") name: String): Response<ArrayList<CountryModel>>?
}