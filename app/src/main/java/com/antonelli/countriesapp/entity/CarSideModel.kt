package com.antonelli.countriesapp.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarSideModel(
    val side: String
): Parcelable