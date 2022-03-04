package by.romanovich.materialdesign.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.romanovich.materialdesign.BuildConfig
import by.romanovich.materialdesign.R

import by.romanovich.materialdesign.repository.asteroid.AsteroidRetrofitImpl
import by.romanovich.materialdesign.repository.asteroid.AsteroidServerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AsteroidViewModel(private val liveData: MutableLiveData<AsteroidData> = MutableLiveData(),
private val asteroidRetrofitImpl: AsteroidRetrofitImpl = AsteroidRetrofitImpl()
) :ViewModel() {
    fun getData(): LiveData<AsteroidData> {
        return liveData
    }

    fun sendRequest(){
        liveData.value = AsteroidData.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveData.value = AsteroidData.Error(R.string.codeError,0)
        } else {
            asteroidRetrofitImpl.getRetrofitImpl().getAsteroid(apiKey).enqueue(callback)
        }
    }




    private val callback = object : Callback<AsteroidServerResponse> {
        override fun onResponse(
            call: Call<AsteroidServerResponse>,
            response: Response<AsteroidServerResponse>
        ) {
            if(response.isSuccessful&&response.body()!=null){
                liveData.value = AsteroidData.Success(response.body()!!)
            }else{
                liveData.value = AsteroidData.Error(R.string.codeError,response.code())
            }
        }


        override fun onFailure(call: Call<AsteroidServerResponse>, t: Throwable) {
            liveData.value = AsteroidData.Error(R.string.codeError,0)
            //https://material.io/components/bottom-navigation/android#theming-a-bottom-navigation-bar
        }

    }
}
