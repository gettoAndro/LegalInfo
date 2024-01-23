package com.peakscircle.legalinfo.domain

import com.google.gson.Gson
import com.peakscircle.legalinfo.data.dto.response.ErrorResponseDTO

abstract class BaseRepository() {

    suspend fun <T : Any> call(call: suspend () -> T): NetworkResult<T> {
        return try {
            val response = call.invoke()
            val baseJson = Gson().toJson(response)
            val baseResponse = Gson().fromJson(baseJson, ErrorResponseDTO::class.java)
            baseResponse.status.let {
                if (it == ERROR) {
                    NetworkResult.Error(ApiException(baseResponse.message))
                } else {
                    NetworkResult.Success(response)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResult.Error(e)
        }
    }

    companion object {
        private const val ERROR = "ERROR"
    }

}

sealed class NetworkResult<out T : Any> {
    data class Success<out T : Any>(val response: T) : NetworkResult<T>()
    data class Error(val exception: Exception) : NetworkResult<Nothing>()
}

class ApiException(message: String) : Exception(message)