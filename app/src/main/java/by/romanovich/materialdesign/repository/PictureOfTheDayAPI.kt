package by.romanovich.materialdesign.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//Выход в сеть
//оформляем запрос
interface PictureOfTheDayAPI {
    @GET("planetary/apod")
    //функция будет (реализовывать ретрофит)передовать запрос поля с названием апи кей, и возрощать PDOServerResponse
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PDOServerResponse>
}