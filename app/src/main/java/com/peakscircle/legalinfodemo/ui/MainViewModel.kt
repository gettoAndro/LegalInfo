package com.peakscircle.legalinfodemo.ui

import com.peakscircle.legalinfo.DocumentType
import com.peakscircle.legalinfo.LegalInfo
import com.peakscircle.legalinfo.domain.NetworkResult
import com.peakscircle.legalinfodemo.BuildConfig
import com.peakscircle.legalinfodemo.ui.common.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel: BaseViewModel() {

    fun configure(hostUrl: String) {
        LegalInfo.getInstance().configure(hostUrl, BuildConfig.DEBUG)
    }

    fun register(userId: String) {
        scope.launch {
            LegalInfo.getInstance().register(userId)
        }
    }

    fun getDocuments(userId: String) {
        scope.launch {
            LegalInfo.getInstance().getDocuments(userId, DocumentType.ALL)
        }
    }

}