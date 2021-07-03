package de.westwing.campaignbrowser.repository

import de.westwing.campaignbrowser.model.CampaignsResponse
import io.reactivex.rxjava3.core.Single

interface CampaignRepository {

    fun getCampaigns(): Single<CampaignsResponse>

}