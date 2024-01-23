package com.peakscircle.legalinfo.data.dto.request

data class GetDocumentsRequestDTO(
    val userId: String,
    val type: String,
    val customType: String? = null
)