package by.romanovich.materialdesign.repository

import by.romanovich.materialdesign.utils.END_POINT_PICTURE_OF_DAY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//Выход в сеть
//оформляем запрос
interface PictureOfTheDayAPI {
    @GET(END_POINT_PICTURE_OF_DAY)
    //функция будет (реализовывать ретрофит)передовать запрос поля с названием апи кей, и возрощать PDOServerResponse
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PDOServerResponse>
}