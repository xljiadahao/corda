package com.r3corda.node.services.query

import com.r3corda.node.services.database.HibernateConfiguration
import com.r3corda.node.services.database.RequeryConfiguration
import io.requery.Persistable
import io.requery.kotlin.eq
import io.requery.kotlin.invoke
import kotlinx.support.jdk7.use
import net.corda.core.node.services.QueryService
import net.corda.core.schemas.MappedSchema
import net.corda.core.utilities.loggerFor
import net.corda.node.services.api.SchemaService
import net.corda.node.services.vault.schemas.VaultSchema
import net.corda.schemas.CashSchemaV1
import org.junit.Assert
import javax.persistence.Query
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.metamodel.EntityType
import kotlin.reflect.KClass

/**
 * A general purpose Query service using Requery (DSL based query that maps to SQL)
 */
class RequeryQueryServiceImpl(val schemaService: SchemaService) : QueryService  {

    companion object {
        val logger = loggerFor<RequeryQueryServiceImpl>()
    }

    val configuration = RequeryConfiguration(schemaService)

    override fun simpleQueryForSchema(sqlString: String, schema: MappedSchema, vararg args: Any?): Iterable<Any?> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun simpleQueryForSchemaUsingNamedArgs(sqlString: String, schema: MappedSchema, vararg args: String): Iterable<Any?> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun nativeQueryForSchema(sqlString: String, schema: MappedSchema, vararg args: Any?): Iterable<Any?> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun namedQueryForSchema(queryName: String, schema: MappedSchema, vararg args: Any?): Iterable<Any?> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T : Persistable> criteriaQueryForSchema(schema: MappedSchema, entityClass: KClass<T>, vararg args: String?): List<T> {
        /*
         * sample usage: SELECT t FROM <T> t
         */
        val results =
            configuration.sessionForSchema(schema).invoke {
                val result = select(entityClass)
                result.get().toList() as List<T> ?: emptyList()
            }
        return results
    }
}


