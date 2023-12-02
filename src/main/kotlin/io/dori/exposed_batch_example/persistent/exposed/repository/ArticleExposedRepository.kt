package io.dori.exposed_batch_example.persistent.exposed.repository

import io.dori.exposed_batch_example.domain.Article
import io.dori.exposed_batch_example.persistent.exposed.entity.ArticleEntity
import io.dori.exposed_batch_example.persistent.exposed.entity.Articles
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class ArticleExposedRepository {
    fun saveArticle(article: Article): Article =
        transaction {
            ArticleEntity.new {
                title = article.title
                content = article.content
                author = article.author
            }.let {
                Article(
                    articleId = it.id.value,
                    title = it.title,
                    content = it.content,
                    author = it.author
                )
            }
        }

    fun saveArticles(articles: List<Article>) =
        transaction {
            Articles.batchInsert(articles) { article ->
                this[Articles.title] = article.title
                this[Articles.content] = article.content
                this[Articles.author] = article.author
            }
        }

    fun findAll(): List<Article> =
        transaction {
            ArticleEntity.all()
                .map {
                    Article(
                        articleId = it.id.value,
                        title = it.title,
                        content = it.content,
                        author = it.author
                    )
                }
        }
}