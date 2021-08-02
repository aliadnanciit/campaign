package de.westwing.campaignbrowser.viewmodel

import android.util.Log
import android.util.SparseArray
import android.util.SparseIntArray
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import de.westwing.campaignbrowser.model.server.CampaignStates
import de.westwing.campaignbrowser.usecase.FetchCampaignListUseCase
import de.westwing.campaignbrowser.usecase.GetCampaignListUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class CampaignViewModel @Inject constructor(
    private val getCampaignListUseCase: GetCampaignListUseCase,
    private val fetchCampaignListUseCase: FetchCampaignListUseCase
) : ViewModel() {

    private val _campaignStateFlow = MutableStateFlow<CampaignStates>(CampaignStates.Loading)
    val campaignsStateFlow: StateFlow<CampaignStates> = _campaignStateFlow

    init {
        getCampaigns()
    }

    private fun getCampaigns() {
        _campaignStateFlow.value = CampaignStates.Loading
        viewModelScope.launch {
            getCampaignListUseCase.execute()
                .catch {
                    _campaignStateFlow.value = CampaignStates.Error(it)
                }
                .collect {
                    if(it.isEmpty()) {
                        _campaignStateFlow.value = CampaignStates.NoContent
                    }
                    else {
                        _campaignStateFlow.value = CampaignStates.Success(it)
                    }
                }
        }
    }


    fun fetchCampaigns() {
//        printHunger()
        viewModelScope.launch {
            fetchCampaignListUseCase.execute()
        }
    }

//    fun printHunger() = runBlocking {
//        launch {
//            delay(200L)
//            Log.i("TESTT", "GER")
//        }
//        coroutineScope {
//            val job = launch {
//                Log.i("TESTT", "H")
//                delay(500L)
//                Log.i("TESTT", "GER")
//            }
//            delay(100L)
//            Log.i("TESTT", "U")
//            job.cancel()
//        }
//        Log.i("TESTT", "N")
//    }
}