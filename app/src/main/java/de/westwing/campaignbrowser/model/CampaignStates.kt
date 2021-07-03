package de.westwing.campaignbrowser.model

sealed class CampaignStates {
    object Loading : CampaignStates()
    data class Success(val list: List<Campaign>) : CampaignStates()
    data class Error(val throwable: Throwable) : CampaignStates()
}
