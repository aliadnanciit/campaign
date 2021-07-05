package de.westwing.campaignbrowser.repository

import de.westwing.campaignbrowser.common.safeApiCall
import de.westwing.campaignbrowser.exception.CampaignNetworkException
import de.westwing.campaignbrowser.model.Campaign
import de.westwing.campaignbrowser.model.server.CampaignsResponse
import de.westwing.campaignbrowser.service.ApiInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class CampaignRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    @Named("IO_DISPATCHER") private val ioDispatcher: CoroutineDispatcher,
    @Named("DEFAULT_DISPATCHER") private val defaultDispatcher: CoroutineDispatcher
) : CampaignRepository {

    override suspend fun getCampaigns(): Flow<List<Campaign>> {
        return flow {
            val response = safeApiCall {
                apiInterface.getCampaigns()
            }
            if (response.isSuccessful) {
                emit(response.body()!!)
            } else {
                throw CampaignNetworkException("Fail to get campaigns due to network error")
            }
        }
        .flowOn(ioDispatcher)
        .map {
            filterValidCampaigns(it)
        }
        .flowOn(defaultDispatcher)
    }

    private fun filterValidCampaigns(it: CampaignsResponse): List<Campaign> {
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