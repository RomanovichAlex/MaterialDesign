package by.romanovich.materialdesign.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.romanovich.materialdesign.BuildConfig
import by.romanovich.materialdesign.R

import by.romanovich.materialdesign.repository.PODServerResponse
import by.romanovich.materialdesign.repository.PictureOfTheDayRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val pictureOfTheDayRetrofitImpl: PictureOfTheDayRetrofitImpl = PictureOfTheDayRetrofitImpl()
) :ViewModel() {
    fun getData():LiveData<AppState>{
        return liveData
    }

    fun sendRequest(){
        liveData.value = AppState.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
            if (apiKey.isBlank()) {
                liveData.value = AppState.Error(R.string.codeError,0)
            } else {
                pictureOfTheDayRetrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(callback)
            }
    }

    fun sendRequest(date:String) {
        liveData.value = AppState.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveData.value = AppState.Error(R.string.codeError,0)
        } else {
            pictureOfTheDayRetrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey,date).enqueue(callback)
        }
    }


    private val callback = object : Callback<PODServerResponse> {
        override fun onResponse(
            call: Call<PODServerResponse>,
            response: Response<PODServerResponse>
        ) {
            if(response.isSuccessful&&response.body()!=null){
                liveData.value = AppState.SuccessPOD(response.body()!!)
            }else{
                liveData.value = AppState.Error(R.string.codeError,response.code())
            }
        }


    override fun onFailure(call: Call<PODServerResponse>, t: Throwable) {
        liveData.value = AppState.Error(R.string.codeError,0)
    //https://material.io/components/bottom-navigation/android#theming-a-bottom-navigation-bar
    }

    }
}
