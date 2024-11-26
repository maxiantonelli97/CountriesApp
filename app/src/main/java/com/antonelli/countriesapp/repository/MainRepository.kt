package com.antonelli.countriesapp.repository

import com.antonelli.countriesapp.entity.CountryModel

interface MainRepository {
    suspend fun getAllCountries(): ArrayList<CountryModel>?

    suspend fun searchCountry(s: String): ArrayList<CountryModel>?
}