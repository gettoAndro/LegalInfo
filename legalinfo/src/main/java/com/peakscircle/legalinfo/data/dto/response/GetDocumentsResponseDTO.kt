package com.peakscircle.legalinfo.data.dto.response

data class GetDocumentsResponseDTO(
    val data: List<DocumentDTO>
)

data class DocumentDTO(
    val id: String,
    val type: String,
    val title: String,
    val url: String,
    val version: String,
    val created: String,
    val accepted: String?
)