package by.romanovich.materialdesign.repository.earthEpic

import by.romanovich.materialdesign.utils.END_POINT_ASTEROID
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EarthEpicAPI {

    // Earth Polychromatic Imaging Camera
    @GET("EPIC/api/natural")
    fun getEPIC(
        @Query("api_key") apiKey: String,
    ): Call<List<EarthEpicServerResponse>>
}