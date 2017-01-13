package net.corda.node.services.vault.schemas

import io.requery.*
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

object VaultSchema {

    @Table(name = "vault_consumed_states")
    @Entity
    interface VaultConsumedState : Persistable {
        @get:Key
        @get:Column(length = 64)
        var tx_id: String
        @get:Key
        var output_index: Int

        var contractStateClassName: String
        var contractStateClassVersion: Int

        var validFrom: LocalDateTime
        var validTo: Instant
    }

    @Table(name = "vault_consumed_fungible_states")
    @Entity
    interface VaultFungibleState : Persistable {
        @get:Key
        @get:Column(length = 64)
        var tx_id: String
        @get:Key
        var output_index: Int

        @get:OneToMany(mappedBy = "key")
        var participants: VaultKey

        @get:OneToOne(mappedBy = "key")
        var ownerKey: VaultKey

        var quantity: Long
        var ccyCode: String

        @get:OneToOne(mappedBy = "key")
        var issuerKey: VaultKey
        var issuerRef: ByteArray

        @get:OneToMany(mappedBy = "key")
        var exitKeys: VaultKey
    }

    @Table(name = "vault_consumed_linear_states")
    @Entity
    interface VaultLinearState : Persistable {
        @get:Key
        @get:Column(length = 64)
        var tx_id: String
        @get:Key
        var output_index: Int

        @get:OneToMany(mappedBy = "key")
        var participants: VaultKey

        @get:OneToOne(mappedBy = "key")
        var ownerKey: VaultKey

        @get:Index("externalId_index")
        var externalId: String
        @get:Column(length = 36, unique = true, nullable = false)
        var uuid: UUID

        var dealRef: String
        @get:OneToMany(mappedBy = "name")
        var dealParties: VaultParty
    }

    @Table(name = "vault_keys")
    @Entity
    interface VaultKey : Persistable {
        @get:Key
        @get:Generated
        var id: Int

        @get:Key
        @get:Column(length = 255)
        @get:ForeignKey
        var key: String
    }

    @Table(name = "vault_parties")
    @Entity
    interface VaultParty : Persistable {
        @get:Key
        @get:Generated
        var id: Int

        @get:ForeignKey
        @get:Key
        var name: String
        @get:ForeignKey
        @get:Key
        @get:Column(length = 255)
        var key: String
    }
}
