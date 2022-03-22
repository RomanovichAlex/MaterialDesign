
package by.romanovich.materialdesign.repository.mars

import com.google.gson.annotations.SerializedName

data class MarsServerResponse(
    @field:SerializedName("img_src") val imgSrc: String?,
    @field:SerializedName("earth_date") val earth_date: String?,
)


