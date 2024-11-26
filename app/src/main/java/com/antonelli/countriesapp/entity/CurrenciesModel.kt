package com.antonelli.countriesapp.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrenciesModel(
    val name: String,
    val symbol: String
): Parcelable