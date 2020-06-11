package com.aptopayments.core.network

import android.os.Build
import androidx.annotation.VisibleForTesting
import com.aptopayments.core.BuildConfig
import com.aptopayments.core.platform.AptoPlatform
import com.aptopayments.core.repository.UserSessionRepository
import okhttp3.Cache
import okhttp3.CertificatePinner
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit

private const val SHA_256_PREFIX = "sha256/"
private const val SSL_FINGERPRINT_ONE = "k2v657xBsOVe1PQRwOsHsw3bsGT2VzIqz5K+59sNQws="
private const val SSL_FINGERPRINT_TWO = "5kJvNEMw0KjrCAu7eXY5HZdvyCS13BbA0VJG1RSP91w="
private const val SSL_FINGERPRINT_THREE = "emrYgpjLplsXa6OnqyXuj5BgQDPaapisB5WfVm+jrFQ="
private const val SSL_API_HOST = "*.link.ledge.me"
private const val CACHE_SIZE_BYTES: Long = 1024 * 1024 * 2

@VisibleForTesting(otherwise = Modifier.PRIVATE)
const val X_CACHE_CONTROL_HEADER = "Cache-Control"
private const val X_CACHE_CONTROL_CONTENT = "private, must-revalidate"

@VisibleForTesting(otherwise = Modifier.PRIVATE)
const val X_DEVICE_HEADER = "X-Device"
private const val X_DEVICE_CONTENT = "Android"

@VisibleForTesting(otherwise = Modifier.PRIVATE)
const val X_SDK_VERSION_HEADER = "X-SDK-Version"

@VisibleForTesting(otherwise = Modifier.PRIVATE)
const val X_DEVICE_VERSION_HEADER = "X-Device-Version"

@VisibleForTesting(otherwise = Modifier.PRIVATE)
const val X_CONTENT_TYPE_HEADER = "Content-Type"
private const val X_CONTENT_TYPE_CONTENT = "application/json"

@VisibleForTesting(otherwise = Modifier.PRIVATE)
const val X_API_KEY = "Api-Key"
const val X_AUTHORIZATION = "Authorization"

private const val TIMEOUT_SECONDS = 30L

internal interface OkHttpClientProvider {
    fun provide(): OkHttpClient
}

internal open class OkHttpClientProviderImpl(
    private val apiKeyProvider: ApiKeyProvider,
    private val userSessionRepository: UserSessionRepository
) : OkHttpClientProvider {

    override fun provide(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        addInterceptors(okHttpClientBuilder)
        configureConnection(okHttpClientBuilder)
        return okHttpClientBuilder.build()
    }

    protected open fun configureConnection(okHttpClientBuilder: OkHttpClient.Builder) {
        addTimeout(okHttpClientBuilder)
        addCertificatePinner(okHttpClientBuilder)
        disableConnectingPool(okHttpClientBuilder)
        manageCache(okHttpClientBuilder)
    }

    private fun addTimeout(okHttpClientBuilder: OkHttpClient.Builder) {
        okHttpClientBuilder.readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }

    private fun addInterceptors(okHttpClientBuilder: OkHttpClient.Builder) {
        addLoggingInterceptor(okHttpClientBuilder)
        addApiKeyInterceptor(okHttpClientBuilder)
        addFixedHeaders(okHttpClientBuilder)
        addTokenInterceptor(okHttpClientBuilder)
    }

    private fun addTokenInterceptor(okHttpClientBuilder: OkHttpClient.Builder) {
        okHttpClientBuilder.addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
            if (userSessionRepository.isUserSessionValid()) {
                newRequest.addHeader(X_AUTHORIZATION, "Bearer ${userSessionRepository.userToken}")
            }
            chain.proceed(newRequest.build())
        }
    }

    private fun addLoggingInterceptor(okHttpClientBuilder: OkHttpClient.Builder) {
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
    }

    private fun addApiKeyInterceptor(okHttpClientBuilder: OkHttpClient.Builder) {
        okHttpClientBuilder.addInterceptor { chain ->
            val newRequest = chain.request().newBuilder().addHeader(X_API_KEY, apiKeyProvider.apiKey).build()
            chain.proceed(newRequest)
        }
    }

    private fun addFixedHeaders(okHttpClientBuilder: OkHttpClient.Builder) {
        okHttpClientBuilder.addInterceptor { chain ->
            val newRequest = chain.request().newBuilder().apply {
                addHeader(X_SDK_VERSION_HEADER, BuildConfig.VERSION_NAME)
                addHeader(X_DEVICE_HEADER, X_DEVICE_CONTENT)
                addHeader(X_DEVICE_VERSION_HEADER, Build.MODEL ?: "Unknown model")
                addHeader(X_CACHE_CONTROL_HEADER, X_CACHE_CONTROL_CONTENT)
                addHeader(X_CONTENT_TYPE_HEADER, X_CONTENT_TYPE_CONTENT)
            }.build()
            chain.proceed(newRequest)
        }
    }

    private fun manageCache(okHttpClientBuilder: OkHttpClient.Builder) {
        AptoPlatform.application.cacheDir?.let { cacheDir ->
            okHttpClientBuilder.cache(Cache(cacheDir, CACHE_SIZE_BYTES))
        }
    }

    private fun disableConnectingPool(okHttpClientBuilder: OkHttpClient.Builder) {
        // Disabling the ConnectionPool to avoid SocketTimeOut Exceptions related to network restarts
        // Source: https://github.com/square/okhttp/issues/3146#issuecomment-311158567
        okHttpClientBuilder.connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
    }

    private fun addCertificatePinner(okHttpClientBuilder: OkHttpClient.Builder) {
        val certificatePinner = CertificatePinner.Builder()
            .add(SSL_API_HOST, SHA_256_PREFIX + SSL_FINGERPRINT_ONE)
            .add(SSL_API_HOST, SHA_256_PREFIX + SSL_FINGERPRINT_TWO)
            .add(SSL_API_HOST, SHA_256_PREFIX + SSL_FINGERPRINT_THREE)
            .build()
        okHttpClientBuilder.certificatePinner(certificatePinner)
    }
}
