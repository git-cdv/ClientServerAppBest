package com.chkan.clientserverappbest.data.models

/**
 * @author Dmytro Chkan on 13.12.2022.
 */
data class Article(
    val source: Source?,
    val title: String,
    val url: String?,
    val description: String?,
    val author: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
)

data class Source(
    val id: String?,
    val name: String,
)