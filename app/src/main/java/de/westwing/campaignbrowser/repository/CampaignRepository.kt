package de.westwing.campaignbrowser.repository

import de.westwing.campaignbrowser.model.server.CampaignsResponse
import kotlinx.coroutines.flow.Flow

interface CampaignRepository {

    suspend fun getCampaigns(): Flow<CampaignsResponse>

}