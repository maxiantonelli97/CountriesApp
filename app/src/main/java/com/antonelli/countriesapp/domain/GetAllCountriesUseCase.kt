package com.antonelli.countriesapp.domain

import com.antonelli.countriesapp.entity.CountryModel
import com.antonelli.countriesapp.repository.MainRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetAllCountriesUseCase @Inject constructor(
    var mainRepository: MainRepository,
    dispatcher: CoroutineDispatcher
) : BaseCoroutinesUseCase<Unit, List<CountryModel>?>(dispatcher) {
    override suspend fun execute(params: Unit): Result<List<CountryModel>?> {
        val lista = mainRepository.getAllCountries()
        return if (lista.isNullOrEmpty()) {
            Result.failure(Throwable("Error"))
        } else {
            Result.success(lista)
        }
    }
}