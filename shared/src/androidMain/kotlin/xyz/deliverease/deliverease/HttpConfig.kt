package xyz.deliverease.deliverease

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

actual fun getBaseUrl() = "https://10.0.2.2:8081"
actual fun getHttpEngine(): HttpClientEngine {
    // TODO("Remove when ready to deploy/push to prod")
    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    })

    val sslContext = SSLContext.getInstance("TLS").apply {
        init(null, trustAllCerts, SecureRandom())
    }

    val okHttpClient = OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier{ _, _ -> true }
        .build()

    return OkHttp.create { preconfigured = okHttpClient }
}

//actual fun getHttpEngine(): HttpClientEngine {
//    val okHttpClient = OkHttpClient.Builder().build()
//    return OkHttp.create { preconfigured = okHttpClient }
//}