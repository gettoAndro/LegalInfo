package com.peakscircle.legalinfo.data

import com.peakscircle.legalinfo.data.dto.request.RegisterRequestDTO
import com.peakscircle.legalinfo.data.dto.response.GetDocumentsResponseDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @POST("documents/register")
    suspend fun register(@Body request: RegisterRequestDTO): Any

    @GET("documents")
    suspend fun getDocuments(): GetDocumentsResponseDTO

    @GET("documents/accepted")
    suspend fun getAcceptedDocuments(): GetDocumentsResponseDTO

    @POST("documents/accept")
    suspend fun acceptDocument(): Any

}