package org.romeo.noteskotlin.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import org.romeo.noteskotlin.model.Repository

abstract class BaseViewModel<T>(val repository: Repository) : ViewModel() {

    val viewStateChannel: BroadcastChannel<T> = BroadcastChannel(Channel.CONFLATED)
    val errorChannel: Channel<Throwable> = Channel(Channel.CONFLATED)

    protected val scope = CoroutineScope(Job() + Dispatchers.IO)
}