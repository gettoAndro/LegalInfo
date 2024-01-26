package com.peakscircle.legalinfo.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.peakscircle.legalinfo.LegalInfo
import com.peakscircle.legalinfo.domain.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DocumentViewModel : ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    val loadingLive = MutableLiveData<Boolean>()

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                DocumentViewModel()
            }
        }
    }

    fun acceptDocument(onResult: (NetworkResult<Any>) -> Unit) {
        scope.launch {
            loadingLive.postValue(true)
            LegalInfo.getInstance().acceptDocument().let {
                loadingLive.postValue(false)
                withContext(Dispatchers.Main) {
                    onResult(it)
                }
            }
        }
    }

}