package de.westwing.campaignbrowser.repository

import app.cash.turbine.test
import de.westwing.campaignbrowser.database.CampaignDao
import de.westwing.campaignbrowser.database.CampaignEntity
import de.westwing.campaignbrowser.model.Campaign
import de.westwing.campaignbrowser.model.server.CampaignDto
import de.westwing.campaignbrowser.model.server.CampaignsMetadata
import de.westwing.campaignbrowser.model.server.CampaignsResponse
import de.westwing.campaignbrowser.model.server.ImageDto
import de.westwing.campaignbrowser.service.ApiInterface
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.time.ExperimentalTime

class CampaignRepositoryTest {

    @MockK
    private lateinit var imageDto: ImageDto

    @MockK
    private lateinit var campaignDto: CampaignDto

    @MockK
    private lateinit var campaignsMetadata: CampaignsMetadata

    @MockK
    private lateinit var campaignEntity: CampaignEntity

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var repository: CampaignRepository

    @MockK
    private lateinit var campaignResponse: CampaignsResponse

    @MockK
    private lateinit var apiInterface: ApiInterface

    @RelaxedMockK
    private lateinit var campaignDao: CampaignDao


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CampaignRepositoryImpl(apiInterface, campaignDao, testCoroutineDispatcher)
    }

    @ExperimentalTime
    @Test
    fun `verify data from local database`() = testCoroutineDispatcher.runBlockingTest {
        val dbData = flowOf(listOf(campaignEntity))

        coEvery { campaignDao.getAllDistinctUntilChanged() } returns dbData
        coEvery { campaignEntity.uid } returns 1
        coEvery { campaignEntity.name } returns "name"
        coEvery { campaignEntity.description } returns "description"
        coEvery { campaignEntity.imageUrl } returns "http://imgurl"

        repository.getCampaigns().test {
            assertEquals(
                listOf(Campaign(
                    id = 1,
                    name = "name",
                    description = "description",
                    imageUrl = "http://imgurl"
                )), expectItem()
            )
            expectComplete()
        }
    }

    @ExperimentalTime
    @Test
    fun `verify get campaigns`() = testCoroutineDispatcher.runBlockingTest {
        coEvery { apiInterface.getCampaigns() } returns Response.success(campaignResponse)
        coEvery { campaignResponse.metadata} returns campaignsMetadata
        coEvery { campaignsMetadata.data} returns listOf(campaignDto)
        coEvery { campaignDto.name } returns "name"
        coEvery { campaignDto.description } returns "description"
        coEvery { campaignDto.image} returns imageDto
        coEvery { imageDto.url } returns ""

        repository.fetchCampaigns()

        coVerify {
            apiInterface.getCampaigns()
        }
        coVerify {
            campaignDao.deleteAll()
        }
        coVerify {
            campaignDao.insertAll(any())
        }
    }


}