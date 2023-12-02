package io.dori.exposed_batch_example.persistent.exposed.repository

import io.dori.exposed_batch_example.domain.Article
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    properties = ["spring.batch.job.enabled=false"]
)
class ArticleExposedRepositoryTest {
    @Autowired
    lateinit var articleExposedRepository: ArticleExposedRepository

    @Test
    fun `save article`() {
        // given
        val article = Article(
            title = "title",
            content = "content",
            author = "author"
        )

        // when
        articleExposedRepository.saveArticle(article)

        // then
        assertEquals(articleExposedRepository.findAll().size, 1)
    }
}