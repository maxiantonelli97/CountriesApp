package com.antonelli.countriesapp.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonelli.countriesapp.domain.GetAllCountriesUseCase
import com.antonelli.countriesapp.domain.SearchUseCase
import com.antonelli.countriesapp.entity.CountryModel
import com.antonelli.countriesapp.enums.StatesEnum
import com.antonelli.countriesapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    val getAllCountriesUseCase: GetAllCountriesUseCase,
    val searchUseCase: SearchUseCase,
) : ViewModel() {

    var searchQuery by mutableStateOf("")
        private set

    var responseEnum = MutableStateFlow<StatesEnum?>(StatesEnum.START)

    var isLoading = MutableStateFlow<Boolean?>(false)

    private var searching = false

    val response: MutableStateFlow<List<CountryModel>?> = MutableStateFlow(null)

    fun getAllCountries() {
        isLoading.value = true
        viewModelScope.launch {
            val result = getAllCountriesUseCase(Unit)
            if (result.isFailure) {
                responseEnum.value = StatesEnum.ERROR
            } else {
                response.value = result.getOrNull()
                responseEnum.value = StatesEnum.SUCCESS
            }
            isLoading.value = false
        }
    }

    fun search(q: String) {
        isLoading.value = true
        viewModelScope.launch {
            if (q.length < 3) {
                if (searching) {
                    val result = searchUseCase(q)
                    if (result.isFailure) {
                        responseEnum.value = StatesEnum.ERROR
                    } else {
                        response.value = result.getOrNull()
                        responseEnum.value = StatesEnum.SUCCESS
                    }
                    searching = false
                }
            } else {
                searching = true
                val result = searchUseCase(q)
                if (result.isFailure) {
                    responseEnum.value = StatesEnum.ERROR
                } else {
                    response.value = result.getOrNull()
                    responseEnum.value = StatesEnum.SUCCESS
                }
            }
            isLoading.value = false
        }
    }

    fun setSearchText(text: String?) {
        searchQuery = text ?: ""
        search(searchQuery)
    }

    fun setEnumNull() {
        responseEnum.value = null
    }
}
