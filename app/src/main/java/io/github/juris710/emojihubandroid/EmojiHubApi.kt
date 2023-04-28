package io.github.juris710.emojihubandroid

import retrofit2.Response
import retrofit2.http.GET

interface EmojiHubApi {
    @GET("/api/random")
    suspend fun getRandomEmoji(): Response<Emoji>
}