package io.dori.exposed_batch_example.job

import io.dori.exposed_batch_example.persistent.exposed.repository.ArticleExposedRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    properties = ["spring.batch.job.enabled=false"]
)
class ArticleBulkInsertJobConfigurationTest {
    @Autowired
    @Qualifier(ArticleBulkInsertJobConfiguration.JOB_NAME)
    lateinit var job: Job

    @Autowired
    lateinit var jobRepository: JobRepository

    @Autowired
    lateinit var jobLauncher: JobLauncher

    @Autowired
    lateinit var articleExposedRepository: ArticleExposedRepository

    @Test
    fun jobTest() {
        val jobExecution = JobLauncherTestUtils()
            .apply {
                jobRepository = this@ArticleBulkInsertJobConfigurationTest.jobRepository
                jobLauncher = this@ArticleBulkInsertJobConfigurationTest.jobLauncher
                job = this@ArticleBulkInsertJobConfigurationTest.job
            }
            .run {
                launchJob()
            }

        assertEquals(jobExecution.status, BatchStatus.COMPLETED)
        assertEquals(articleExposedRepository.findAll().size, 1000)
    }
}