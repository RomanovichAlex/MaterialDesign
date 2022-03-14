package by.romanovich.materialdesign.viewmodel

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.romanovich.materialdesign.BuildConfig
import by.romanovich.materialdesign.R

import by.romanovich.materialdesign.repository.earthEpic.EarthEpicRetrofitImpl
import by.romanovich.materialdesign.repository.earthEpic.EarthEpicServerResponse
import by.romanovich.materialdesign.repository.mars.MarsPhotosServerResponse
import by.romanovich.materialdesign.repository.mars.MarsRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class PhotoEarthViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData(),

) :ViewModel() {
    private val earthEpicRetrofitImpl: EarthEpicRetrofitImpl by lazy { EarthEpicRetrofitImpl() }
    private val marsRetrofitImpl: MarsRetrofitImpl by lazy { MarsRetrofitImpl() }


    fun getData(): LiveData<AppState> {
        return liveData
    }

    fun sendRequestEarth() {
        liveData.value = AppState.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveData.value = AppState.Error(R.string.codeError, 0)
        } else {
            this.earthEpicRetrofitImpl.getRetrofitImpl().getEPIC(apiKey).enqueue(callback)
        }
    }

    fun sendRequestMars() {
        liveData.postValue(AppState.Loading(0))
        val earthDate = getDayBeforeYesterday()
        this.marsRetrofitImpl.getMarsRetrofitImpl().getMarsImageByDate(earthDate,BuildConfig.NASA_API_KEY).enqueue(callbackMars)
    }

    fun getDayBeforeYesterday(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val yesterday = LocalDateTime.now().minusDays(2)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return yesterday.format(formatter)
        } else {
            val cal: Calendar = Calendar.getInstance()
            val s = SimpleDateFormat("yyyy-MM-dd")
            cal.add(Calendar.DAY_OF_YEAR, - 2)
            return s.format(cal.time)
        }
    }



    private val callbackMars = object : Callback<MarsPhotosServerResponse> {

        override fun onResponse(
            call: Call<MarsPhotosServerResponse>,
            response: Response<MarsPhotosServerResponse>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveData.value = AppState.SuccessMars(response.body() !!)
            } else {
                liveData.value = AppState.Error(R.string.codeError, response.code())
            }
        }

        override fun onFailure(call: Call<MarsPhotosServerResponse>, t: Throwable) {
            liveData.value = AppState.Error(R.string.codeError, 0)
        }
    }


        private val callback = object : Callback<List<EarthEpicServerResponse>> {

            override fun onResponse(
                call: Call<List<EarthEpicServerResponse>>,
                response: Response<List<EarthEpicServerResponse>>,
            ) {
                if (response.isSuccessful && response.body() != null) {
                    liveData.value = AppState.SuccessEarthEpic(response.body() !!)
                } else {
                    liveData.value = AppState.Error(R.string.codeError, response.code())
                }
            }


            override fun onFailure(call: Call<List<EarthEpicServerResponse>>, t: Throwable) {
                liveData.value = AppState.Error(R.string.codeError, 0)
            }

        }
    }


