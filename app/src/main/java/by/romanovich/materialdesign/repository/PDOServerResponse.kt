package by.romanovich.materialdesign.repository

import com.google.gson.annotations.SerializedName

//дто файл который будет возращать ретрофит по запросу к наса
data class PDOServerResponse (
        @SerializedName("date") val date: String?,
        val explanation: String,
        val hdurl: String,

        @SerializedName("media_type")
        val mediaType: String,

        @SerializedName("service_version")
        val serviceVersion: String,
        val title: String,
        val url: String

)