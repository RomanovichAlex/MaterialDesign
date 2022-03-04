package by.romanovich.materialdesign.viewmodel

import by.romanovich.materialdesign.repository.asteroid.AsteroidServerResponse

sealed class AsteroidData {
    data class Loading(val progress:Int): AsteroidData()
    data class Success(var asteroidData: AsteroidServerResponse): AsteroidData()
    data class Error(val error:Int, val code:Int): AsteroidData()

}
