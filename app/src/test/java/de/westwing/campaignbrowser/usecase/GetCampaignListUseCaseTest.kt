package de.westwing.campaignbrowser.usecase

import app.cash.turbine.test
import de.westwing.campaignbrowser.model.Campaign
import de.westwing.campaignbrowser.model.server.CampaignDto
import de.westwing.campaignbrowser.model.server.CampaignsMetadata
import de.westwing.campaignbrowser.model.server.CampaignsResponse
import de.westwing.campaignbrowser.model.server.ImageDto
import de.westwing.campaignbrowser.repository.CampaignRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

private const val NAME = "name"
private const val DESCRIPTION = "description"
private const val IMAGE_URL = "url"
private const val EMPTY = ""

class GetCampaignListUseCaseTest {

    @MockK
    private lateinit var imageDto: ImageDto

    @MockK
    private lateinit var campaignDto: CampaignDto

    @MockK
    private lateinit var metaData: CampaignsMetadata

    @MockK
    private lateinit var campaignRepository: CampaignRepository

    private lateinit var getCampaignListUseCase: GetCampaignListUseCase

    @MockK
    private lateinit var campaignsResponse: CampaignsResponse

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getCampaignListUseCase = GetCampaignListUseCase(campaignRepository, testCoroutineDispatcher)
    }

    @ExperimentalTime
    @Test
    fun `when all name and description are not null then map response`() {
        every { campaignsResponse.metadata } returns metaData
        every { metaData.data } returns listOf(campaignDto)
        every { campaignDto.name } returns NAME
        every { campaignDto.description } returns DESCRIPTION
        every { campaignDto.image } returns imageDto
        every { imageDto.url } returns IMAGE_URL
        coEvery { campaignRepository.getCampaigns() } returns flowOf(campaignsResponse)

        runBlockingTest {
            getCampaignListUseCase.execute().test {
                assertEquals(
                    listOf(Campaign(name = NAME, description = DESCRIPTION, imageUrl = IMAGE_URL))
                    , expectItem()
                )
                expectComplete()
            }
        }

        coVerify {
            campaignRepository.getCampaigns()
        }
    }

    @ExperimentalTime
    @Test
    fun `remove campaign if campaign name is null`() {
        every { campaignsResponse.metadata } returns metaData
        every { metaData.data } returns listOf(campaignDto)
        every { campaignDto.name } returns null
        every { campaignDto.description } returns DESCRIPTION
        every { campaignDto.image } returns imageDto
        every { imageDto.url } returns IMAGE_URL
        coEvery { campaignRepository.getCampaigns() } returns flowOf(campaignsResponse)

        runBlockingTest {
            getCampaignListUseCase.execute().test {
                assertEquals(emptyList<Campaign>(), expectItem())
                expectComplete()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `remove campaign if campaign name is empty`() {
        every { campaignsResponse.metadata } returns metaData
        every { metaData.data } returns listOf(campaignDto)
        every { campaignDto.name } returns EMPTY
        every { campaignDto.description } returns DESCRIPTION
        every { campaignDto.image } returns imageDto
        every { imageDto.url } returns IMAGE_URL
        coEvery { campaignRepository.getCampaigns() } returns flowOf(campaignsResponse)

        runBlockingTest {
            getCampaignListUseCase.execute().test {
                assertEquals(emptyList<Campaign>(), expectItem())
                expectComplete()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `remove campaign if description name is null`() {
        every { campaignsResponse.metadata } returns metaData
        every { metaData.data } returns listOf(campaignDto)
        every { campaignDto.name } returns NAME
        every { campaignDto.description } returns null
        every { campaignDto.image } returns imageDto
        every { imageDto.url } returns IMAGE_URL
        coEvery { campaignRepository.getCampaigns() } returns flowOf(campaignsResponse)

        runBlockingTest {
            getCampaignListUseCase.execute().test {
                assertEquals(emptyList<Campaign>(), expectItem())
                expectComplete()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `remove campaign if campaign description is empty`() {
        every { campaignsResponse.metadata } returns metaData
        every { metaData.data } returns listOf(campaignDto)
        every { campaignDto.name } returns NAME
        every { campaignDto.description } returns EMPTY
        every { campaignDto.image } returns imageDto
        every { imageDto.url } returns IMAGE_URL
        coEvery { campaignRepository.getCampaigns() } returns flowOf(campaignsResponse)

        runBlocking {
            getCampaignListUseCase.execute().test {
                assertEquals(emptyList<Campaign>(), expectItem())
                expectComplete()
            }
        }
    }
}