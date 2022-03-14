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

class AsteroidViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData(),
private val asteroidRetrofitImpl: AsteroidRetrofitImpl = AsteroidRetrofitImpl()
) :ViewModel() {
    fun getData(): LiveData<AppState> {
        return liveData
    }

    fun sendRequest(){
        liveData.value = AppState.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveData.value = AppState.Error(R.string.codeError,0)
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
                liveData.value = AppState.SuccessAsteroid(response.body()!!)
            }else{
                liveData.value = AppState.Error(R.string.codeError,response.code())
            }
        }


        override fun onFailure(call: Call<AsteroidServerResponse>, t: Throwable) {
            liveData.value = AppState.Error(R.string.codeError,0)
            //https://material.io/components/bottom-navigation/android#theming-a-bottom-navigation-bar
        }

    }
}
