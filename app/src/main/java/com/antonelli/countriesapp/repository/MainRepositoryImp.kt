package com.antonelli.countriesapp.repository

import com.antonelli.countriesapp.api.ApiService
import com.antonelli.countriesapp.entity.CountryModel
import javax.inject.Inject

class MainRepositoryImp @Inject constructor(
    private val apiService: ApiService,
) : MainRepository {
    override suspend fun getAllCountries(): ArrayList<CountryModel>? {
        try {
            val response = apiService.getAllCountries()
            return if (response != null && response.isSuccessful && response.body() != null) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun searchCountry(s: String): ArrayList<CountryModel>? {
        try {
            val response = apiService.searchCountry(s)
            return if (response != null && response.isSuccessful && response.body() != null) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            return null
        }
    }
}