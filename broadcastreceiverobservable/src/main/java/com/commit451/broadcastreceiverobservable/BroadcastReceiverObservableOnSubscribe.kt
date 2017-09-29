package com.commit451.broadcastreceiverobservable

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe

internal class BroadcastReceiverObservableOnSubscribe(private val context: Context, private val intentFilter: IntentFilter, private val broadcastPermission: String? = null, private val schedulerHandler: Handler? = null) : ObservableOnSubscribe<Intent> {

    override fun subscribe(e: ObservableEmitter<Intent>) {
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                e.onNext(intent)
            }
        }
        context.registerReceiver(broadcastReceiver, intentFilter, broadcastPermission, schedulerHandler)

        e.setCancellable { context.unregisterReceiver(broadcastReceiver) }
    }
}