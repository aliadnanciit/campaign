package de.westwing.campaignbrowser.repository

import de.westwing.campaignbrowser.model.server.CampaignsResponse
import de.westwing.campaignbrowser.service.ApiInterface
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test

class CampaignRepositoryTest {

    private lateinit var repository: CampaignRepository

    @MockK
    private lateinit var campaignResponse: CampaignsResponse

    @MockK
    private lateinit var apiInterface: ApiInterface

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CampaignRepositoryImpl(apiInterface)
    }

    @Test
    fun `verify get campaigns`() {
        every { apiInterface.getCampaigns() } returns Single.just(campaignResponse)
        repository.getCampaigns()

        verify { apiInterface.getCampaigns() }
    }
}