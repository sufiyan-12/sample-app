package com.prac.sampleappxml.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prac.sampleappxml.activity.models.Meme
import com.prac.sampleappxml.activity.network.retrofit.ServiceApiRetrofit
import kotlinx.coroutines.launch

class MemeViewModel: ViewModel() {

    private val serviceApiRetrofit = ServiceApiRetrofit()
    private val _memeList = MutableLiveData<List<Meme>>()
    val memeList: LiveData<List<Meme>> = _memeList
    companion object{
        private const val LOGGER = "MemeViewModel"
    }

    fun syncMemeList(count: Int){
        viewModelScope.launch {
            try {
                val data = serviceApiRetrofit.getMemesList(count)
                if (data.isSuccessful){
                    val response = data.body()
                    if(response != null){
                        _memeList.postValue(response.memes)
                    }
                }
                else{
                    Log.e(LOGGER,"syncMemeList() case failed--  ${data.raw()}",null)
                }
            }catch (e : Exception){
                Log.e(LOGGER,"syncMemeList() failed --- ",e)
            }
        }
    }

}