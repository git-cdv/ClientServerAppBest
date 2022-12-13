package com.chkan.clientserverappbest.data.models

import androidx.annotation.IntRange
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Dmytro Chkan on 13.12.2022.
 */
@Serializable
data class ArticlesResponse(
        @SerialName("status") val status: String,
        @SerialName("totalResults") @IntRange(from = 1) val totalResults: Int,
        @SerialName("message") val message: String? = null,
        @SerialName("articles") val articles: List<ArticleDto>,
    )

@Serializable
data class ArticleDto(
    @SerialName("source") val source: SourceDto? = null,
    @SerialName("title") val title: String = "",
    @SerialName("url") val url: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("author") val author: String? = null,
    @SerialName("urlToImage") val urlToImage: String? = null,
    @SerialName("publishedAt") val publishedAt: String? = null,
    @SerialName("content") val content: String? = null,
)

@Serializable
data class SourceDto(
    @SerialName("id") val id: String?,
    @SerialName("name") val name: String,
)

internal fun ArticleDto.toArticle(): Article {
    return Article(
        source = this.source?.toSource(),
        title = title,
        url = url,
        description = description,
        author = author,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}

private fun SourceDto.toSource(): Source {
    return Source(id = id, name = name)
}