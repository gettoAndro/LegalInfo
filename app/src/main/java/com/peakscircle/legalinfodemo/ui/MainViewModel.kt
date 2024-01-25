package com.peakscircle.legalinfodemo.ui

import com.peakscircle.legalinfo.DocumentType
import com.peakscircle.legalinfo.LegalInfo
import com.peakscircle.legalinfo.data.dto.response.DocumentDTO
import com.peakscircle.legalinfo.domain.NetworkResult
import com.peakscircle.legalinfodemo.BuildConfig
import com.peakscircle.legalinfodemo.ui.common.BaseViewModel
import com.peakscircle.legalinfodemo.ui.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel : BaseViewModel() {

    val documents = SingleLiveEvent<List<DocumentDTO>>()
    val acceptedDocuments = SingleLiveEvent<List<DocumentDTO>>()
    fun configure(hostUrl: String) {
        LegalInfo.getInstance().configure(hostUrl, BuildConfig.DEBUG)
        messageLive.postValue("Configured")
    }

    fun register(userId: String) {
        scope.launch {
            loadingLive.postValue(true)
            LegalInfo.getInstance().register(userId).let {
                loadingLive.postValue(false)
                when (it) {
                    is NetworkResult.Success -> messageLive.postValue("Registered")
                    is NetworkResult.Error -> messageLive.postValue(it.exception.message)
                }
            }
        }
    }

    fun getDocuments(userId: String) {
        scope.launch {
            loadingLive.postValue(true)
            LegalInfo.getInstance().getDocuments(userId, DocumentType.ALL).let {
                loadingLive.postValue(false)
                when (it) {
                    is NetworkResult.Success -> {
                        documents.postValue(it.response.data)
                    }

                    is NetworkResult.Error -> messageLive.postValue(it.exception.message)
                }
            }
        }
    }

    fun getAcceptedDocuments(userId: String) {
        scope.launch {
            loadingLive.postValue(true)
            LegalInfo.getInstance().getAcceptedDocuments(userId).let {
                loadingLive.postValue(false)
                when (it) {
                    is NetworkResult.Success -> {
                        acceptedDocuments.postValue(it.response.data)
                    }
                    is NetworkResult.Error -> messageLive.postValue(it.exception.message)
                }
            }
        }
    }

}