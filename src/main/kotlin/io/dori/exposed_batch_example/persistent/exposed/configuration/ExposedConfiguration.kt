package io.dori.exposed_batch_example.persistent.exposed.configuration

import io.dori.exposed_batch_example.persistent.exposed.entity.Articles
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class ExposedConfiguration(
    private val dataSource: DataSource
) {
    init {
        Database.connect(dataSource)
    }
}