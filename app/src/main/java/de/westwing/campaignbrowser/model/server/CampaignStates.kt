package de.westwing.campaignbrowser.model.server

import de.westwing.campaignbrowser.model.Campaign

sealed class CampaignStates {

    object Loading : CampaignStates()

    data class Success(val list: List<Campaign>) : CampaignStates()

    data class Error(val throwable: Throwable) : CampaignStates()
}
