
package by.romanovich.materialdesign.repository.weather

import android.os.Parcel
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

data class WeatherServerResponse(
    val flrID: String,
    val beginTime: String,
    val peakTime: String,
    val endTime: Any? = null,
    val classType: String,
    val sourceLocation: String,
    val activeRegionNum: Long,
    val link: String
)
