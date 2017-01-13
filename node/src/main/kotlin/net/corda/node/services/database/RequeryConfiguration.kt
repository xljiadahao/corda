package com.r3corda.node.services.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.requery.Persistable
import io.requery.sql.KotlinConfiguration
import io.requery.sql.KotlinEntityDataStore
import io.requery.sql.SchemaModifier
import io.requery.sql.TableCreationMode
import net.corda.core.crypto.SecureHash
import net.corda.core.schemas.MappedSchema
import net.corda.core.utilities.loggerFor
import net.corda.node.services.api.SchemaService
import net.corda.schemas.Models
import org.h2.jdbcx.JdbcDataSource
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class RequeryConfiguration(val schemaService: SchemaService) {

    // TODO:
    // 1. schemaService schemaOptions needs to be applied: eg. default schema, table prefix
    // 2. user Exposed Transaction Manager ??? (see NodeDatabaseConnectionProvider getConnection())
    // 3. set other generic database configuration options: show_sql, format_sql
    // Note: Annotations are pre-processed using (kapt) so no need to register dynamically

    val config = HikariConfig(configureDataSourceProperties())
    val dataSource = HikariDataSource(config)
    val configuration = KotlinConfiguration(dataSource = dataSource,
                                            model = Models.SCHEMAS,
                                            useDefaultLogging = true)
    init {
        val tables = SchemaModifier(configuration)
        val mode = TableCreationMode.DROP_CREATE
        tables.createTables(mode)
    }

    companion object {
        val logger = loggerFor<RequeryConfiguration>()
    }

    // TODO: make this a guava cache or similar to limit ability for this to grow forever.
    private val sessionFactories = ConcurrentHashMap<MappedSchema, KotlinEntityDataStore<Persistable>>()

    fun sessionForSchema(schema: MappedSchema): KotlinEntityDataStore<Persistable> {
        return sessionFactories.computeIfAbsent(schema, { makeSessionFactoryForSchema(it) })
    }

    protected fun makeSessionFactoryForSchema(schema: MappedSchema): KotlinEntityDataStore<Persistable> {
        return KotlinEntityDataStore<Persistable>(configuration)
    }

    /**
     * Make properties appropriate for creating a DataSource
     *
     * @param nodeName Reflects an instance of the in-memory database.  Defaults to a random string.
     */
    private fun configureDataSourceProperties(): Properties {
        val props = Properties()
        props.setProperty("dataSourceClassName", "org.h2.jdbcx.JdbcDataSource")
        props.setProperty("dataSource.url", "jdbc:h2:mem:corda;DB_CLOSE_ON_EXIT=FALSE")
        props.setProperty("dataSource.user", "sa")
        props.setProperty("dataSource.password", "")
        return props
    }
}