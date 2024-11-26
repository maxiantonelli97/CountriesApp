package com.antonelli.countriesapp.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryNameModel(
    val common: String = "",
    val official: String? = null,
): Parcelable