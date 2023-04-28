package io.github.juris710.emojihubandroid

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EmojiHubApi {
    @GET("/api/random")
    suspend fun getRandomEmoji(): Response<Emoji>

    @GET("/api/all/category/{category}")
    suspend fun getAllEmojisOfCategory(@Path("category") category: String): Response<List<Emoji>>
}