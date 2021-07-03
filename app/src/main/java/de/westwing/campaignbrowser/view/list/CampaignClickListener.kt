package de.westwing.campaignbrowser.view.list

import de.westwing.campaignbrowser.model.Campaign

interface CampaignClickListener {
    fun onclick(campaign: Campaign)
}