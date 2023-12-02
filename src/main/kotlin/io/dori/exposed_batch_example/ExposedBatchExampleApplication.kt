package io.dori.exposed_batch_example

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableBatchProcessing
@SpringBootApplication
class ExposedBatchExampleApplication

fun main(args: Array<String>) {
    runApplication<ExposedBatchExampleApplication>(*args)
}
