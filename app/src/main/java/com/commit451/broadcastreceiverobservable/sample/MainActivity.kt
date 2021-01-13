package com.commit451.broadcastreceiverobservable.sample

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.commit451.broadcastreceiverobservable.BroadcastReceiverObservable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context = applicationContext
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

        val text = findViewById<TextView>(R.id.text)

        BroadcastReceiverObservable.create(context, filter)
                .startWithItem(Intent())
                .map {
                    status(context)
                }
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { connected ->
                    text.text = if (connected) "connected" else "disconnected"
                }
    }

    private fun status(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return null != activeNetwork && activeNetwork.isConnected
    }
}
