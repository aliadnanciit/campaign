package de.westwing.campaignbrowser.usecase

import de.westwing.campaignbrowser.model.Campaign
import de.westwing.campaignbrowser.model.server.CampaignsResponse
import de.westwing.campaignbrowser.repository.CampaignRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetCampaignListUseCase @Inject constructor(
    private val campaignRepository: CampaignRepository
) {

    fun execute(): Single<List<Campaign>> {
        return campaignRepository.getCampaigns()
            .map {
                filterValidCampaigns(it)
            }
    }

    private fun filterValidCampaigns(it: CampaignsResponse) =
        it.metadata.data.filter { campaignDto ->
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