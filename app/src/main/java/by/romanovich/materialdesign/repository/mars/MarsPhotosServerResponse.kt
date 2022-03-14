
package by.romanovich.materialdesign.repository.mars

import com.google.gson.annotations.SerializedName

data class MarsPhotosServerResponse(
    @field:SerializedName("photos") val photos: ArrayList<MarsServerResponse>,
)

