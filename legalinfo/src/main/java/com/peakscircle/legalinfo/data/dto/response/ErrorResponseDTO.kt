package com.peakscircle.legalinfo.data.dto.response

import com.google.gson.annotations.SerializedName

class ErrorResponseDTO(
    @SerializedName("status") val status: String,
    @SerializedName("error") val message: String)