package tech.relaycorp.ping.common

import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import java.util.*

fun <T> PublishFlow() = BroadcastChannel<T>(1)

fun <T> Flow<Optional<T>>.element(): Flow<T> =
    flatMapLatest {
        if (it.isPresent) flowOf(it.get()!!) else emptyFlow()
    }

