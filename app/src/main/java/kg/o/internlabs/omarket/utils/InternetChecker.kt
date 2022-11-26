package kg.o.internlabs.omarket.utils

import android.content.Context
import android.net.*
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class InternetChecker(context: Context) : LiveData<NetworkStatus>(){
    val internetList: ArrayList<Network> = ArrayList()
    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback
    val connectivityManager: ConnectivityManager = context
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun onActive() {
        super.onActive()
        connectivityManagerCallback = internetCallBack()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, connectivityManagerCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
    }

    private fun internetCallBack() = object: ConnectivityManager.NetworkCallback(){
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            val networkCapability = connectivityManager.getNetworkCapabilities(network)
            val hasConnection = networkCapability?.hasCapability(NetworkCapabilities
                .NET_CAPABILITY_INTERNET)?:false
            if (hasConnection){
                determine(network)
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            internetList.remove(network)
            internetList.clear()
            announceStatus()
        }

        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
                determine(network)
            } else {
                internetList.remove(network)
            }
            announceStatus()
        }

    }

    private fun determine(network: Network) {
        CoroutineScope(Dispatchers.IO).launch {
            if (InternetAvailability.check()){
                withContext(Dispatchers.Main){
                    internetList.add(network)
                    announceStatus()
                }
            }
        }
    }

    private fun announceStatus() {
        if (internetList.isNotEmpty()){
            postValue(NetworkStatus.Available)
        } else {
            postValue(NetworkStatus.Unavailable)
        }
    }
}