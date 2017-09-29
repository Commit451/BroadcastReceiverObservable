package com.commit451.broadcastreceiverobservable

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import io.reactivex.Observable

object BroadcastReceiverObservable {

    fun create(context: Context,
               intentFilter: IntentFilter,
               broadcastPermission: String? = null,
               schedulerHandler: Handler? = null): Observable<Intent> {
        return Observable.create(BroadcastReceiverObservableOnSubscribe(context, intentFilter, broadcastPermission, schedulerHandler))
    }
}