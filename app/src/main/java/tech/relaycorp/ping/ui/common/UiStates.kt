package tech.relaycorp.ping.ui.common

import android.system.Os.accept
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.sendBlocking

object Click

fun BroadcastChannel<Click>.clicked() = this.sendBlocking(Click)

object Finish
fun BroadcastChannel<Finish>.finish() = this.sendBlocking(Finish)
