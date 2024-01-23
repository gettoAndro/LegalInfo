package com.peakscircle.legalinfodemo.ui

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
            Timber.d("REGISTER")
            LegalInfo.getInstance().register(userId).let {
                when(it) {
                    is NetworkResult.Success -> {
                        Timber.d("register SUCCESS")
                    }
                    is NetworkResult.Error -> Timber.e(it.exception)
                }
            }
        }
    }

}