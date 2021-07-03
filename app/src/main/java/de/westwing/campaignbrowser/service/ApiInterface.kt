package de.westwing.campaignbrowser.service

import de.westwing.campaignbrowser.model.server.CampaignsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiInterface {

    @GET("cms/test/campaigns.json")
    fun getCampaigns(): Single<CampaignsResponse>
}