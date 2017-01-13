package net.corda.node.services.vault.schemas

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.requery.Persistable
import io.requery.kotlin.eq
import io.requery.kotlin.invoke
import io.requery.sql.KotlinConfiguration
import io.requery.sql.KotlinEntityDataStore
import io.requery.sql.SchemaModifier
import io.requery.sql.TableCreationMode
import net.corda.node.services.vault.schemas.VaultSchema.VaultFungibleState
import net.corda.testing.node.makeTestDataSourceProperties
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.util.*

class VaultSchemaTest {

//    var instance : KotlinEntityDataStore<Persistable>? = null
//    val data : KotlinEntityDataStore<Persistable> get() = instance!!
//
//    @Before
//    fun setUp() {
//        val config = HikariConfig(makeTestDataSourceProperties())
//        val dataSource = HikariDataSource(config)
//        val configuration = KotlinConfiguration(dataSource = dataSource, model = Models.DEFAULT, useDefaultLogging = true)
//        instance = KotlinEntityDataStore<Persistable>(configuration)
//        val tables = SchemaModifier(configuration)
//        tables.dropTables()
//        val mode = TableCreationMode.CREATE
//        tables.createTables(mode)
//    }
//
//    @After
//    fun tearDown() {
//        data.close()
//    }
//
//    companion object {
//        fun randomFungibleState(): VaultFungibleState {
//            val random = Random()
//            val state = VaultFungibleStateEntity()
//            state.tx_id = "12345"
//            state.output_index = 0
//            state.quantity = random.nextLong()
//            val ccyCodes = arrayOf("GBP","USD","CHF","EUR")
//            state.ccyCode = ccyCodes[random.nextInt(ccyCodes.size)]
//            state.participants = VaultKeyEntity()
//            state.issuerKey = VaultKeyEntity()
//            state.issuerRef = byteArrayOf(1)
//            state.exitKeys = VaultKeyEntity()
//            return state
//        }
//    }
//
//
//    @Test
//    fun `insert into fungible state`() {
//        val fungibleState = randomFungibleState()
//        data.invoke {
//            insert(fungibleState)
//            val result = select(VaultFungibleState::class) where (VaultFungibleState::tx_id eq fungibleState.tx_id) limit 10
//            Assert.assertSame(result().first(), fungibleState)
//        }
//    }
//
//    @Test
//    fun `query first from fungible state`() {
//        data.invoke {
//            val result = select(VaultFungibleState::class) where (VaultFungibleState::ccyCode eq "GBP") limit 5
//            val first = result.get().first()
//            println(first)
//            assertNotNull(first)
//        }
//    }
}