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
        liveData.postValue(PictureOfTheDayData.Loading(null))
        pictureOfTheDayRetrofitImpl.getRetrofitImpl().getPictureOfTheDay(BuildConfig.NASA_API_KEY).enqueue(
            object : Callback<PDOServerResponse> {
                //делаем колбэк получаем респонсе
                override fun onResponse(
                    call: Call<PDOServerResponse>,
                    response: Response<PDOServerResponse>
                ) {
                    //обрабатываем ответ
                    if(response.isSuccessful&&response.body()!=null){
                        //передаем в лайвдату сукцесс
                        response.body()?.let {
                            liveData.postValue(PictureOfTheDayData.Success(it))
                        }
                    // если ответ неудачный
                    }else{
                        liveData.postValue(PictureOfTheDayData.Error(R.string.codeError,response.code()))
                    }

                }
                override fun onFailure(call: Call<PDOServerResponse>, t: Throwable) {
                    liveData.postValue(PictureOfTheDayData.Error(R.string.codeError,0))
                }
            }
        )
    }
}