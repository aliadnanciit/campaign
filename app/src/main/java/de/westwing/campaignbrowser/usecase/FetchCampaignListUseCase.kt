package de.westwing.campaignbrowser.usecase

import de.westwing.campaignbrowser.repository.CampaignRepository
import javax.inject.Inject

class FetchCampaignListUseCase @Inject constructor(
    private val campaignRepository: CampaignRepository
) {

    suspend fun execute() {
        return campaignRepository.fetchCampaigns()
    }
}