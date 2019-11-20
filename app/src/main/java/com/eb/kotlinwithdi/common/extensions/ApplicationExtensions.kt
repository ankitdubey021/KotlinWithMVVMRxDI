package com.eb.kotlinandjavaarefriends.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.provider.Settings




inline fun <reified T:Any> newIntent(context: Context): Intent = Intent(context,T::class.java)

inline fun <reified T : Any> Activity.launchActivity(
        requestCode:Int =-1,
        options: Bundle?=null,
        noinline init: Intent.() -> Unit = {}){

    val intent = newIntent<T>(this)
    intent.init()
    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN)
        startActivityForResult(intent,requestCode,options)
    else
        startActivityForResult(intent,requestCode)
}


fun Activity.isNetworkAvailable(): Boolean {
    val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            // for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        val nwInfo = connectivityManager.activeNetworkInfo ?: return false
        return nwInfo.isConnected
    }
}


