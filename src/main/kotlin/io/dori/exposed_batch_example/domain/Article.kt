package io.dori.exposed_batch_example.domain

class Article(
    val articleId: Long = 0L,
    val title: String,
    val content: String,
    val author: String
)