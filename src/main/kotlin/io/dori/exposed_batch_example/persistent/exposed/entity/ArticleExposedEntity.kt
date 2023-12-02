package io.dori.exposed_batch_example.persistent.exposed.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object Articles: LongIdTable(name = "article", columnName = "article_id") {
    val title = varchar("title", 255)
    val content = text("content")
    val author = varchar("author", 100)
}

class ArticleEntity(id: EntityID<Long>): LongEntity(id) {
    companion object: LongEntityClass<ArticleEntity>(Articles)

    var title by Articles.title
    var content by Articles.content
    var author by Articles.author
}