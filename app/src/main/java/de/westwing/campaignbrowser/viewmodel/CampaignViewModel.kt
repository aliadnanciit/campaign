package de.westwing.campaignbrowser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.westwing.campaignbrowser.model.server.CampaignStates
import de.westwing.campaignbrowser.usecase.GetCampaignListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CampaignViewModel @Inject constructor(
    private val getCampaignListUseCase: GetCampaignListUseCase
) : ViewModel() {

    private val _campaignLiveData = MutableLiveData<CampaignStates>()
    val campaignsLiveData : LiveData<CampaignStates> = _campaignLiveData

    fun getCampaigns() {
        _campaignLiveData.value = CampaignStates.Loading

        viewModelScope.launch {
            try {
                val result = getCampaignListUseCase.execute()
                _campaignLiveData.value = CampaignStates.Success(result)
            }
            catch (throwable: Throwable) {
                _campaignLiveData.value = CampaignStates.Error(throwable)
            }
        }
    }
}