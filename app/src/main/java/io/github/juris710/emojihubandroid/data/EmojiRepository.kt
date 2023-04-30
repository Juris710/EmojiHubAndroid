package io.github.juris710.emojihubandroid.data

import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

@ActivityScoped
class EmojiRepository @Inject constructor(
    private val api: EmojiHubApi
) {
    private suspend fun <T> wrapHttpRequest(
        execHttpRequest: suspend () -> T
    ): HttpResult<T> {
        return try {
            val response = execHttpRequest()
            HttpResult.Success(response)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is IOException -> "You might not have Internet connection."
                is HttpException -> "Request Failed with status code ${e.code()}"
                else -> "Unknown Error"
            }
            HttpResult.Error(errorMessage)
        }
    }

    suspend fun getRandomEmoji(): HttpResult<Emoji> {
        return wrapHttpRequest {
            api.getRandomEmoji()
        }
    }

    suspend fun getAllEmojisOfCategory(category: String): HttpResult<List<Emoji>> {
        return wrapHttpRequest {
            api.getAllEmojisOfCategory(category)
        }
    }
}