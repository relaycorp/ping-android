package tech.relaycorp.ping.common

import kotlinx.coroutines.channels.BroadcastChannel

fun <E> PublishFlow() = BroadcastChannel<E>(1)
