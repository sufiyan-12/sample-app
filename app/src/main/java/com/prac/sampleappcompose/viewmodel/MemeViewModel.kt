package com.prac.sampleappcompose.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prac.sampleappcompose.models.Meme
import com.prac.sampleappcompose.retrofit.ServiceApiRetrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MemeViewModel: ViewModel() {

    private val serviceApiRetrofit = ServiceApiRetrofit()

    private val _images = MutableStateFlow<List<String?>>(emptyList())
    val images: StateFlow<List<String?>> get() = _images

    private val _memeList = MutableStateFlow<List<Meme>>(emptyList())
    val memeList: StateFlow<List<Meme>> get() = _memeList
    init {
        // sync meme list
        syncMemeList()
    }

    companion object{
        private const val LOGGER = "MemeViewModel"
    }

    private fun syncMemeList(){
        viewModelScope.launch {
            try {
                val data = serviceApiRetrofit.getMemesList(40)
                if (data.isSuccessful){
                    val response = data.body()
                    if(response != null){
                        _memeList.value = response.memes
                        // create a list of image urls
                        setUpCarouselImages(response.memes)
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

    private fun setUpCarouselImages(list: List<Meme>){
        val urls = list.map {  item -> item.image }.shuffled().take(5)
        _images.value = urls
    }
}