package de.westwing.campaignbrowser.repository

import app.cash.turbine.test
import de.westwing.campaignbrowser.model.server.CampaignsResponse
import de.westwing.campaignbrowser.service.ApiInterface
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.time.ExperimentalTime

class CampaignRepositoryTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var repository: CampaignRepository

    @MockK
    private lateinit var campaignResponse: CampaignsResponse

    @MockK
    private lateinit var apiInterface: ApiInterface

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CampaignRepositoryImpl(apiInterface, testCoroutineDispatcher)
    }

    @ExperimentalTime
    @Test
    fun `verify get campaigns`() = testCoroutineDispatcher.runBlockingTest {
        coEvery { apiInterface.getCampaigns() } returns Response.success(campaignResponse)

        repository.getCampaigns().test {
            assertEquals(campaignResponse, expectItem())
            expectComplete()
        }

        coVerify { apiInterface.getCampaigns() }
    }
}