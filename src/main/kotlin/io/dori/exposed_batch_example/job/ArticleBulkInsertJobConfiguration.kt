package io.dori.exposed_batch_example.job

import io.dori.exposed_batch_example.domain.Article
import io.dori.exposed_batch_example.persistent.exposed.repository.ArticleExposedRepository
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.support.ListItemReader
import org.springframework.batch.support.transaction.ResourcelessTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ArticleBulkInsertJobConfiguration(
    @Suppress("SpringJavaInjectionPointsAutowiringInspection") private val jobBuilderFactory: JobBuilderFactory,
    @Suppress("SpringJavaInjectionPointsAutowiringInspection") private val stepBuilderFactory: StepBuilderFactory,
    private val articleExposedRepository: ArticleExposedRepository
) {
    companion object {
        const val JOB_NAME = "articleBulkInsertJob"
    }

    @Bean(JOB_NAME)
    fun job() = jobBuilderFactory[JOB_NAME]
        .preventRestart()
        .start(step())
        .build()

    @Bean("${JOB_NAME}_step")
    fun step() = stepBuilderFactory["${JOB_NAME}_step"]
        .chunk<Article, Article>(100)
        .reader(itemReader())
        .writer(itemWriter())
        .transactionManager(ResourcelessTransactionManager())
        .build()

    @StepScope
    @Bean("${JOB_NAME}_reader")
    fun itemReader(): ListItemReader<Article> {
        val articles = mutableListOf<Article>()
        for (i in 1..1000) {
            articles.add(
                Article(
                    title = "title$i",
                    content = "content$i",
                    author = "author$i"
                )
            )
        }
        return ListItemReader(articles)
    }

    @StepScope
    @Bean("${JOB_NAME}_writer")
    fun itemWriter(): ItemWriter<Article> = ItemWriter { items ->
        articleExposedRepository.saveArticles(items)
    }
}