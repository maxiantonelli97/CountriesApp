package com.antonelli.countriesapp.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryModel(
    val name: CountryNameModel? = null,
    val capital: ArrayList<String?>? = null,
    val flags: CountryFlagsModel? = null,
    val region: String? = null,
    val subregion: String? = null,
    val currencies: Map<String, CurrenciesModel>? = null,
    val languages: Map<String, String>? = null,
    val population: Int? = null,
    val car: CarSideModel? = null,
    var coatOfArms: ArmsModel? = null
): Parcelable