package de.westwing.campaignbrowser.usecase

import de.westwing.campaignbrowser.model.Campaign
import de.westwing.campaignbrowser.repository.CampaignRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCampaignListUseCase @Inject constructor(
    private val campaignRepository: CampaignRepository
) {

    suspend fun execute(): Flow<List<Campaign>> {
        return campaignRepository.getCampaigns()
    }
}