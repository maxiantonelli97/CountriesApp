package com.antonelli.countriesapp.domain

import com.antonelli.countriesapp.entity.CountryModel
import com.antonelli.countriesapp.repository.MainRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    var mainRepository: MainRepository,
    dispatcher: CoroutineDispatcher
) : BaseCoroutinesUseCase<String, List<CountryModel>?>(dispatcher) {
    override suspend fun execute(params: String): Result<List<CountryModel>?> {
        val lista = mainRepository.searchCountry(params)
        return if (lista.isNullOrEmpty()) {
            Result.failure(Throwable("Error"))
        } else {
            Result.success(lista)
        }
    }
}