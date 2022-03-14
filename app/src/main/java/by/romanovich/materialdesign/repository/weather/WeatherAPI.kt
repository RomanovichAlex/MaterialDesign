package by.romanovich.materialdesign.repository.weather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("DONKI/FLR")
    fun getSolarFlare(
        @Query("api_key") apiKey: String,
        @Query("startDate") startDate: String,
    ): Call<List<WeatherServerResponse>>
}