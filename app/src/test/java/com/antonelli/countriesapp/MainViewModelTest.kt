package com.antonelli.countriesapp

import com.antonelli.countriesapp.domain.GetAllCountriesUseCase
import com.antonelli.countriesapp.domain.SearchUseCase
import com.antonelli.countriesapp.entity.CountryModel
import com.antonelli.countriesapp.ui.main.MainViewModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @MockK
    private val getAllCountriesUseCase = mockk<GetAllCountriesUseCase>()

    @MockK
    private val searchUseCase = mockk<SearchUseCase>()

    private val mainViewModel = MainViewModel(getAllCountriesUseCase, searchUseCase)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `search with 0 char string return all countries`(): Unit =
        runBlocking {
            var expected: List<CountryModel>? = null
            var result: List<CountryModel>? = null
            // Given
            coEvery {
                getAllCountriesUseCase(Unit)
            } returns Result.success(fakeAllCountriesList)

            // When
            mainViewModel.search("")
            expected = mainViewModel.response.value

            // Given
            coEvery {
                searchUseCase("")
            } returns Result.success(fakeAllCountriesList)

            // When
            mainViewModel.search("")
            result = mainViewModel.response.value


            // Then
            val aux = expected == result

            assert(aux)
        }

    @Test
    fun `search with 2 char string dosnt return all countries`(): Unit =
        runBlocking {
            var expected: List<CountryModel>? = null
            var result: List<CountryModel>? = null
            // Given
            coEvery {
                getAllCountriesUseCase(Unit)
            } returns Result.success(fakeAllCountriesList)

            // When
            mainViewModel.search("")
            expected = mainViewModel.response.value

            // Given
            coEvery {
                searchUseCase("ar")
            } returns Result.success(fakeAllCountriesList)

            // When
            mainViewModel.search("ar")
            result = mainViewModel.response.value


            // Then
            val aux = expected == result

            assert(aux)
        }

    @Test
    fun `search with 3 char string dosnt return all countries`(): Unit =
        runBlocking {
            var expected: List<CountryModel>? = null
            var result: List<CountryModel>? = null
            // Given
            coEvery {
                getAllCountriesUseCase(Unit)
            } returns Result.success(fakeAllCountriesList)

            // When
            mainViewModel.search("")
            expected = mainViewModel.response.value

            // Given
            coEvery {
                searchUseCase("arg")
            } returns Result.success(fakeSomeCountriesList)

            // When
            mainViewModel.search("arg")
            result = mainViewModel.response.value


            // Then
            val aux = expected != result

            assert(aux)
        }


    private companion object {
        val fakeAllCountriesList = listOf(CountryModel(), CountryModel(), CountryModel())
        val fakeSomeCountriesList = listOf(CountryModel())
    }
}
