package de.westwing.campaignbrowser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.westwing.campaignbrowser.model.server.CampaignStates
import de.westwing.campaignbrowser.usecase.GetCampaignListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CampaignViewModel @Inject constructor(
    private val getCampaignListUseCase: GetCampaignListUseCase
) : ViewModel() {

    private val _campaignLiveData = MutableStateFlow<CampaignStates>(CampaignStates.Loading)
    val campaignsLiveData: StateFlow<CampaignStates> = _campaignLiveData

    fun getCampaigns() {
        _campaignLiveData.value = CampaignStates.Loading

        viewModelScope.launch {

            getCampaignListUseCase.execute()
                .catch {
                    _campaignLiveData.value = CampaignStates.Error(it)
                }
                .collect {
                    _campaignLiveData.value = CampaignStates.Success(it)
                }
        }
    }
}