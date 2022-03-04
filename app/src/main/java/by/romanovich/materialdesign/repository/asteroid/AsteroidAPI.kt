package by.romanovich.materialdesign.repository.asteroid

import by.romanovich.materialdesign.utils.END_POINT_ASTEROID
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidAPI {

    @GET(END_POINT_ASTEROID)
    fun getAsteroid(
        @Query("api_key") apiKey:String): Call<AsteroidServerResponse>

}