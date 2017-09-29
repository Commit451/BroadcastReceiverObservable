# BroadcastReceiverObservable
Turn a BroadCastReceiver into an Observable

[![Build Status](https://travis-ci.org/Commit451/BroadcastReceiverObservable.svg?branch=master)](https://travis-ci.org/Commit451/BroadcastReceiverObservable) [![](https://jitpack.io/v/Commit451/BroadcastReceiverObservable.svg)](https://jitpack.io/#Commit451/BroadcastReceiverObservable)

# Usage
To start listening for connectivity change events:
```kotlin
val text = findViewById<TextView>(R.id.text)

val context = applicationContext
val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

BroadcastReceiverObservable.create(context, filter)
        .startWith(Intent())
        .map {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            activeNetwork != null && activeNetwork.isConnected
        }
        .distinctUntilChanged()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { connected ->
            text.text = if (connected) "connected" else "disconnected"
        }
```

It is recommended that you use some method to cancel the subscription when the activity or fragment is destroyed so that the `BroadcastReceiver` gets unregistered. [RxLifecycle](https://github.com/trello/RxLifecycle) is a great tool for this.

# Acknowledgements
BroadcastReceiverObservable was inspired by [rxnetwork-android](https://github.com/Laimiux/rxnetwork-android) and modified a bit to make it work with RxJava 2.

License
--------

    Copyright 2017 Commit 451

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
