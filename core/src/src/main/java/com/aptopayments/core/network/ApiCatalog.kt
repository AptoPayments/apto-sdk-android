package com.aptopayments.core.network

import android.os.Build
import com.aptopayments.core.BuildConfig
import com.aptopayments.core.platform.AptoPlatform
import com.aptopayments.core.platform.AptoSdkEnvironment
import com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction.ContentEntity
import com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction.WorkflowActionConfigurationEntity
import com.aptopayments.core.repository.cardapplication.remote.parser.ContentParser
import com.aptopayments.core.repository.cardapplication.remote.parser.WorkflowActionConfigurationParser
import com.aptopayments.core.repository.user.remote.entities.DataPointEntity
import com.aptopayments.core.repository.user.remote.parser.DataPointParser
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.CertificatePinner
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.*

private const val SHA_256_PREFIX = "sha256/"
private const val SSL_FINGERPRINT_ONE = "k2v657xBsOVe1PQRwOsHsw3bsGT2VzIqz5K+59sNQws="
private const val SSL_FINGERPRINT_TWO = "5kJvNEMw0KjrCAu7eXY5HZdvyCS13BbA0VJG1RSP91w="
private const val SSL_FINGERPRINT_THREE = "emrYgpjLplsXa6OnqyXuj5BgQDPaapisB5WfVm+jrFQ="
private const val SSL_API_HOST = "*.link.ledge.me"
private const val CACHE_SIZE_BYTES: Long = 1024 * 1024 * 2
private const val X_CACHE_CONTROL_HEADER = "Cache-Control"
private const val X_CACHE_CONTROL_CONTENT = "private, must-revalidate"
private const val X_DEVICE_HEADER = "X-Device"
private const val X_DEVICE_CONTENT = "Android"
private const val X_SDK_VERSION_HEADER = "X-SDK-Version"
private const val X_DEVICE_VERSION_HEADER = "X-Device-Version"
private const val X_CONTENT_TYPE_HEADER = "Content-Type"
private const val X_CONTENT_TYPE_CONTENT = "application/json"

class ApiCatalog {

    fun api(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(environment.baseUrl)
                .client(createClient())
                .addConverterFactory(getConverterFactory())
                .build()
    }

    fun vaultApi(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(environment.vaultBaseUrl)
                .client(createClient())
                .addConverterFactory(getConverterFactory())
                .build()
    }

    private fun getConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(gson())
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        val certificatePinner = CertificatePinner.Builder()
                .add(SSL_API_HOST, SHA_256_PREFIX + SSL_FINGERPRINT_ONE)
                .add(SSL_API_HOST, SHA_256_PREFIX + SSL_FINGERPRINT_TWO)
                .add(SSL_API_HOST, SHA_256_PREFIX + SSL_FINGERPRINT_THREE)
                .build()
        okHttpClientBuilder.certificatePinner(certificatePinner)
        // Disabling the ConnectionPool to avoid SocketTimeOut Exceptions related to network restarts
        // Source: https://github.com/square/okhttp/issues/3146#issuecomment-311158567
        okHttpClientBuilder.connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
        // Cache (Etag)
        AptoPlatform.cacheDir?.let { cacheDir ->
            okHttpClientBuilder.cache(Cache(cacheDir, CACHE_SIZE_BYTES))
        }
        // Fixed headers
        okHttpClientBuilder.addInterceptor { chain ->
            val newRequest = chain.request().newBuilder().apply {
                addHeader(X_SDK_VERSION_HEADER, BuildConfig.VERSION_NAME)
                addHeader(X_DEVICE_HEADER, X_DEVICE_CONTENT)
                addHeader(X_DEVICE_VERSION_HEADER, Build.MODEL)
                addHeader(X_CACHE_CONTROL_HEADER, X_CACHE_CONTROL_CONTENT)
                addHeader(X_CONTENT_TYPE_HEADER, X_CONTENT_TYPE_CONTENT)
            }.build()
            chain.proceed(newRequest)
        }
        return okHttpClientBuilder.build()
    }

    companion object {
        var gson: Gson? = null
        var apiKey = ""
        var environment: AptoSdkEnvironment = AptoSdkEnvironment.PRD
        fun set(apiKey: String, environment: AptoSdkEnvironment) {
            this.apiKey = apiKey
            this.environment = environment
        }

        fun gson(): Gson {
            return gson ?: run {
                val gsonBuilder = GsonBuilder()
                gsonBuilder.registerTypeAdapter(DataPointEntity::class.java, DataPointParser())
                gsonBuilder.registerTypeAdapter(WorkflowActionConfigurationEntity::class.java,
                        WorkflowActionConfigurationParser())
                gsonBuilder.registerTypeAdapter(ContentEntity::class.java, ContentParser())
                val gson = gsonBuilder.serializeNulls().create()
                this.gson = gson
                return gson
            }
        }
    }
}
