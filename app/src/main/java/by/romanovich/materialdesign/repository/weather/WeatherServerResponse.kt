
package by.romanovich.materialdesign.repository.weather


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
