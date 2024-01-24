package com.peakscircle.legalinfo

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.peakscircle.legalinfo.data.Api
import com.peakscircle.legalinfo.data.dto.response.GetDocumentsResponseDTO
import com.peakscircle.legalinfo.domain.LegalInfoRepository
import com.peakscircle.legalinfo.domain.NetworkResult
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class LegalInfo {

    companion object {
        private var instance: LegalInfo? = null

        fun getInstance(): LegalInfo {
            if (instance == null) instance = LegalInfo()

            return instance as LegalInfo
        }
    }

    private lateinit var url: String
    private lateinit var api: Api
    private lateinit var repository: LegalInfoRepository

    fun configure(hostUrl: String, debug: Boolean) {
        url = hostUrl

        val builder = OkHttpClient.Builder()

        if (debug) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }

        val okHttpClient = builder
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        api = Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .serializeNulls()
                        .create()
                )
            )
            .client(okHttpClient)
            .build().create(Api::class.java)
        repository = LegalInfoRepository(api)
    }

    suspend fun register(userId: String): NetworkResult<*> {
        if (userId.isBlank()) return NetworkResult.Error(WrongIdException())
        if (::api.isInitialized && ::repository.isInitialized) {
            return repository.register(userId)
        }

        return NetworkResult.Error(NotConfiguredException())
    }

    suspend fun getDocuments(userId: String, type: DocumentType, customType: String? = null): NetworkResult<GetDocumentsResponseDTO> {
        if (userId.isBlank()) return NetworkResult.Error(WrongIdException())
        if (::api.isInitialized && ::repository.isInitialized) {
            return repository.getDocuments(userId, type, customType)
        }

        return NetworkResult.Error(NotConfiguredException())
    }


    class NotConfiguredException : Exception("NOT_CONFIGURED")
    class WrongIdException : Exception("WRONG_ID")
}