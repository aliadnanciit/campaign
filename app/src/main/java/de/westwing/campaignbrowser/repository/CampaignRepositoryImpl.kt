package de.westwing.campaignbrowser.repository

import de.westwing.campaignbrowser.common.safeApiCall
import de.westwing.campaignbrowser.exception.CampaignNetworkException
import de.westwing.campaignbrowser.model.server.CampaignsResponse
import de.westwing.campaignbrowser.service.ApiInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class CampaignRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    @Named("IO_DISPATCHER") private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CampaignRepository {

    override suspend fun getCampaigns(): CampaignsResponse {
        val response = withContext(dispatcher) {
            safeApiCall {
                apiInterface.getCampaigns()
            }
        }
        if(response.isSuccessful) {
            return response.body()!!
        }

        throw CampaignNetworkException("Fail to get campaigns due to network error")
    }
}