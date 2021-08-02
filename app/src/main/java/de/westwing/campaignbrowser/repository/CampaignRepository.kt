package de.westwing.campaignbrowser.repository

import de.westwing.campaignbrowser.model.Campaign
import kotlinx.coroutines.flow.Flow

interface CampaignRepository {

    suspend fun getCampaigns(): Flow<List<Campaign>>

    suspend fun fetchCampaigns()

}