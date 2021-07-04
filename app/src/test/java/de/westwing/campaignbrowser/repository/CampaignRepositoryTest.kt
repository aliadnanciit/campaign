package de.westwing.campaignbrowser.repository

import de.westwing.campaignbrowser.model.server.CampaignsResponse
import de.westwing.campaignbrowser.service.ApiInterface
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

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
        coEvery { apiInterface.getCampaigns() } returns Response.success(campaignResponse)

        runBlocking {
            repository.getCampaigns()
        }

        coVerify { apiInterface.getCampaigns() }
    }
}