package by.romanovich.materialdesign.repository.weather

import by.romanovich.materialdesign.utils.BASE_URL
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRetrofitImpl {
    //создаем экземпляр по какому делаем запрос
    private val baseUrl = BASE_URL

    //возвращаем запрос
    fun getRetrofitImpl(): WeatherAPI {
        val podRetrofitImpl = Retrofit.Builder()
            //в билдер передаем базовый адрес
            .baseUrl(baseUrl)
            //передаем конвертер с жсон в пдосервреспонс
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
        return podRetrofitImpl.create(WeatherAPI::class.java)
    }

}