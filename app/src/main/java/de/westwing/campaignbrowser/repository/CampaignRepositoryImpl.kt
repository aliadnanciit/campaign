package de.westwing.campaignbrowser.repository

import de.westwing.campaignbrowser.common.safeApiCall
import de.westwing.campaignbrowser.database.CampaignDao
import de.westwing.campaignbrowser.database.CampaignEntity
import de.westwing.campaignbrowser.exception.CampaignNetworkException
import de.westwing.campaignbrowser.model.Campaign
import de.westwing.campaignbrowser.model.server.CampaignsResponse
import de.westwing.campaignbrowser.service.ApiInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class CampaignRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    private val campaignDao: CampaignDao,
    @Named("IO_DISPATCHER") private val ioDispatcher: CoroutineDispatcher
) : CampaignRepository {

    override suspend fun getCampaigns(): Flow<List<Campaign>> {
        return campaignDao.getAllDistinctUntilChanged()
            .map {
                it.map { entity ->
                    Campaign(
                        id = entity.uid,
                        name = entity.name,
                        description = entity.description,
                        imageUrl = entity.imageUrl
                    )
                }
            }
//            .flatMapConcat {
//                fetchCampaigns() // this call causes infinite loop
//                flowOf(it)
//            }
            .flowOn(ioDispatcher)
    }

    override suspend fun fetchCampaigns() {
        withContext(ioDispatcher) {
            val response = safeApiCall {
                apiInterface.getCampaigns()
            }
            if (response.isSuccessful) {
                val list = filterValidCampaigns(response.body()!!)
                saveCampaigns(list)
            } else {
                throw CampaignNetworkException("Fail to get campaigns due to network error")
            }
        }
    }

    private fun saveCampaigns(it: List<Campaign>) {
        val list = it.map { campaign ->
            CampaignEntity(
                uid = campaign.id,
                name = campaign.name,
                description = campaign.description,
                imageUrl = campaign.imageUrl
            )
        }
        if (list.isNotEmpty()) {
            campaignDao.deleteAll()
            campaignDao.insertAll(list)
        }
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