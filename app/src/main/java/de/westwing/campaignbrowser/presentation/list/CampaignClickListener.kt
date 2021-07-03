package de.westwing.campaignbrowser.presentation.list

import de.westwing.campaignbrowser.model.Campaign

interface CampaignClickListener {
    fun onclick(campaign: Campaign)
}