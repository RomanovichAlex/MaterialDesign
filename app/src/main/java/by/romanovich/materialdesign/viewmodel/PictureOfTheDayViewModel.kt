package by.romanovich.materialdesign.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.romanovich.materialdesign.BuildConfig
import by.romanovich.materialdesign.R

import by.romanovich.materialdesign.repository.PDOServerResponse
import by.romanovich.materialdesign.repository.PictureOfTheDayRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val pictureOfTheDayRetrofitImpl: PictureOfTheDayRetrofitImpl = PictureOfTheDayRetrofitImpl()
) :ViewModel() {
    fun getData():LiveData<PictureOfTheDayData>{
        return liveData
    }
    fun sendRequest(){
        liveData.value = PictureOfTheDayData.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
            if (apiKey.isBlank()) {
                liveData.value = PictureOfTheDayData.Error(R.string.codeError,0)
            } else {
                pictureOfTheDayRetrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(callback)
            }
    }

    fun sendRequest(date:String) {
        liveData.value = PictureOfTheDayData.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveData.value = PictureOfTheDayData.Error(R.string.codeError,0)
        } else {
            pictureOfTheDayRetrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey,date).enqueue(callback)
        }
    }


    private val callback = object : Callback<PDOServerResponse> {
        override fun onResponse(
            call: Call<PDOServerResponse>,
            response: Response<PDOServerResponse>
        ) {
            if(response.isSuccessful&&response.body()!=null){
                liveData.value = PictureOfTheDayData.Success(response.body()!!)
            }else{
                liveData.value = PictureOfTheDayData.Error(R.string.codeError,response.code())
            }
        }


    override fun onFailure(call: Call<PDOServerResponse>, t: Throwable) {
        liveData.value = PictureOfTheDayData.Error(R.string.codeError,0)
    //https://material.io/components/bottom-navigation/android#theming-a-bottom-navigation-bar
    }

    }
}
