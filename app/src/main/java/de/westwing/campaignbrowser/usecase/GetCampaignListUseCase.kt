package de.westwing.campaignbrowser.usecase

import de.westwing.campaignbrowser.model.Campaign
import de.westwing.campaignbrowser.model.server.CampaignsResponse
import de.westwing.campaignbrowser.repository.CampaignRepository
import javax.inject.Inject

class GetCampaignListUseCase @Inject constructor(
    private val campaignRepository: CampaignRepository
) {

    suspend fun execute(): List<Campaign> {
        return filterValidCampaigns(campaignRepository.getCampaigns())
    }

    private fun filterValidCampaigns(it: CampaignsResponse) : List<Campaign> {
        return it.metadata.data.filter { campaignDto ->
            campaignDto.name.isNullOrBlank().not()
                    && campaignDto.description.isNullOrBlank().not()
        }.map { campaignDto ->
            Campaign(
                name = campaignDto.name!!,
                description = campaignDto.description!!,
                imageUrl = campaignDto.image.url
            )
        }
    }
}