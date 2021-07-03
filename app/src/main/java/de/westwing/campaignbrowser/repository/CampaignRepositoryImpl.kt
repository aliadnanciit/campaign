package de.westwing.campaignbrowser.repository

import de.westwing.campaignbrowser.model.server.CampaignsResponse
import de.westwing.campaignbrowser.service.ApiInterface
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CampaignRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : CampaignRepository {

    override fun getCampaigns(): Single<CampaignsResponse> {
        return apiInterface.getCampaigns()
    }
}