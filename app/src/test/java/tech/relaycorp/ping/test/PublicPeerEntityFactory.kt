package tech.relaycorp.ping.test

import tech.relaycorp.ping.data.database.entity.PublicPeerEntity
import java.util.*

object PublicPeerEntityFactory {
    fun build() = PublicPeerEntity(
        privateAddress = UUID.randomUUID().toString(),
        publicAddress = "example.org"
    )
}
