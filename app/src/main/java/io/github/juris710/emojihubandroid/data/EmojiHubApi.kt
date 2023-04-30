package io.github.juris710.emojihubandroid.data

import retrofit2.http.GET
import retrofit2.http.Path

interface EmojiHubApi {
    @GET("/api/random")
    suspend fun getRandomEmoji(): Emoji

    @GET("/api/all/category/{category}")
    suspend fun getAllEmojisOfCategory(@Path("category") category: String): List<Emoji>
}