package de.westwing.campaignbrowser.repository

import de.westwing.campaignbrowser.model.server.CampaignsResponse

interface CampaignRepository {

    suspend fun getCampaigns(): CampaignsResponse

}