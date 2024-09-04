package com.prac.sampleappxml.activity.models

import com.google.gson.annotations.SerializedName

data class Meme(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("author")
    val subTitle: String? = null,
    @SerializedName("url")
    val image: String? = null
)


data class MemeModel(
    @SerializedName("count")
    val count: Int,
    @SerializedName("memes")
    val memes: List<Meme>
)
