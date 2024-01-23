package com.peakscircle.legalinfo.data

import com.peakscircle.legalinfo.data.dto.request.RegisterRequestDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("register")
    suspend fun register(@Body request: RegisterRequestDTO): Any

}