package com.peakscircle.legalinfo.domain

import com.peakscircle.legalinfo.DocumentType
import com.peakscircle.legalinfo.data.Api
import com.peakscircle.legalinfo.data.dto.request.GetDocumentsRequestDTO
import com.peakscircle.legalinfo.data.dto.request.RegisterRequestDTO

class LegalInfoRepository(private val api: Api) : BaseRepository() {

    suspend fun register(userId: String) = call { api.register(RegisterRequestDTO(userId)) }

    suspend fun getDocuments(userId: String, type: DocumentType, customType: String? = null) =
        call { api.getDocuments(/*GetDocumentsRequestDTO(userId, type.name.lowercase(), customType)*/) }

}