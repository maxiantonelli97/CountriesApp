package com.antonelli.countriesapp.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryFlagsModel(
    val png: String? = null
): Parcelable