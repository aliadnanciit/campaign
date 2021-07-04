package de.westwing.campaignbrowser.service

import de.westwing.campaignbrowser.model.server.CampaignsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("cms/test/campaigns.json")
    suspend fun getCampaigns(): Response<CampaignsResponse>
}