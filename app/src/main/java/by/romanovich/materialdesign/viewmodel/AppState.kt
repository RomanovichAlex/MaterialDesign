package by.romanovich.materialdesign.viewmodel

import by.romanovich.materialdesign.repository.PODServerResponse
import by.romanovich.materialdesign.repository.asteroid.AsteroidServerResponse
import by.romanovich.materialdesign.repository.earthEpic.EarthEpicServerResponse
import by.romanovich.materialdesign.repository.mars.MarsPhotosServerResponse

//sealed - тк про состояние
sealed class AppState {
    //приходит ответ сервера
    data class SuccessPOD(val serverResponse: PODServerResponse) : AppState()

    data class SuccessEarthEpic (val serverResponseEarthEpicData: List<EarthEpicServerResponse>) : AppState()

    data class SuccessMars(val serverResponseMarsData: MarsPhotosServerResponse) : AppState()

    data class SuccessAsteroid(var asteroidData: AsteroidServerResponse): AppState()

    data class Error(val error:Int, val code:Int) : AppState()

    data class Loading(val progress: Int?) : AppState()



}
