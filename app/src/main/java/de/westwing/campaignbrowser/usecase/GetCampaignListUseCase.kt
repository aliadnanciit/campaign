package de.westwing.campaignbrowser.usecase

import de.westwing.campaignbrowser.model.Campaign
import de.westwing.campaignbrowser.model.server.CampaignsResponse
import de.westwing.campaignbrowser.repository.CampaignRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class GetCampaignListUseCase @Inject constructor(
    private val campaignRepository: CampaignRepository,
    @Named("DEFAULT_DISPATCHER") private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun execute(): Flow<List<Campaign>> {
        return campaignRepository.getCampaigns().map {
            filterValidCampaigns(it)
        }
            .flowOn(defaultDispatcher)
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