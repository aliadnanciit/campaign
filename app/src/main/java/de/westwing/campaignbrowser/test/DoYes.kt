package de.westwing.campaignbrowser.test

import de.westwing.campaignbrowser.database.CampaignDao
import de.westwing.campaignbrowser.service.ApiInterface
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class DoYes @Inject constructor(
    private val apiInterface: ApiInterface,
    private val campaignDao: CampaignDao,
    @Named("IO_DISPATCHER") private val ioDispatcher: CoroutineDispatcher
): Yes {
    override fun hello(): String {
        return "Hello Test 3"
    }
}