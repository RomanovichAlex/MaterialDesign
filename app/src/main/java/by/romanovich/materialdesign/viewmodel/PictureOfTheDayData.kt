package by.romanovich.materialdesign.viewmodel

import by.romanovich.materialdesign.repository.PDOServerResponse

//sealed - тк про состояние
sealed class PictureOfTheDayData {
    //приходит ответ сервера
    data class Success(val serverResponse: PDOServerResponse) : PictureOfTheDayData()

    data class Error(val error: Throwable) : PictureOfTheDayData()

    data class Loading(val progress: Int?) : PictureOfTheDayData()
}