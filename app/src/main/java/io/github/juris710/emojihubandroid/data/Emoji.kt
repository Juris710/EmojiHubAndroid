package io.github.juris710.emojihubandroid.data

import timber.log.Timber

data class Emoji(
    val name: String,
    val category: String,
    val group: String,
    val htmlCode: List<String>,
    val unicode: List<String>
) {
    private fun unicodeFromCodePoints(codePoints: List<Int>): String {
        val builder = StringBuilder()
        codePoints.forEach {
            builder.appendCodePoint(it)
        }
        return builder.toString()
    }

    @JvmName("unicodeFromCodePointStrings")
    private fun unicodeFromCodePoints(codePoints: List<String>): String {
        return unicodeFromCodePoints(codePoints.map {
            if (!it.startsWith("U+")) {
                throw IllegalArgumentException("Invalid codepoint $it")
            }
            it.substring(2).toInt(16)
        })
    }

    val emojiAsString: String
        get() {
            return try {
                unicodeFromCodePoints(unicode)
            } catch (e: Exception) {
                Timber.e(e)
                "?"
            }
        }
}