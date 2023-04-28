package io.github.juris710.emojihubandroid

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: EmojiHubApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://emojihub.yurace.pro")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmojiHubApi::class.java)
    }
}