package de.westwing.campaignbrowser.repository

import de.westwing.campaignbrowser.common.safeApiCall
import de.westwing.campaignbrowser.exception.CampaignNetworkException
import de.westwing.campaignbrowser.model.server.CampaignsResponse
import de.westwing.campaignbrowser.service.ApiInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named

class CampaignRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    @Named("IO_DISPATCHER") private val ioDispatcher: CoroutineDispatcher
) : CampaignRepository {

    override suspend fun getCampaigns(): Flow<CampaignsResponse> = flow {
        val response = safeApiCall {
            apiInterface.getCampaigns()
        }

        if (response.isSuccessful) {
            emit(response.body()!!)
        }
        else {
            throw CampaignNetworkException("Fail to get campaigns due to network error")
        }
    }
        .flowOn(ioDispatcher)
}