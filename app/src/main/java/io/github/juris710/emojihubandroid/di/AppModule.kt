package io.github.juris710.emojihubandroid.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.juris710.emojihubandroid.data.EmojiHubApi
import io.github.juris710.emojihubandroid.data.EmojiRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideEmojiRepository(
        api: EmojiHubApi
    ) = EmojiRepository(api)

    @Singleton
    @Provides
    fun provideEmojiHubApi(): EmojiHubApi = Retrofit.Builder()
        .baseUrl("https://emojihub.yurace.pro")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(EmojiHubApi::class.java)
}