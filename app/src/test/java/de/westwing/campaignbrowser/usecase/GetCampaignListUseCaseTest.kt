package de.westwing.campaignbrowser.usecase

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
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

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

    /*@Before
    fun setUp() {
        MockKAnnotations.init(this)
        getCampaignListUseCase = GetCampaignListUseCase(campaignRepository)
    }

    @Test
    fun `when all name and description are not null then map response`() {
        every { campaignsResponse.metadata } returns metaData
        every { metaData.data } returns listOf(campaignDto)
        every { campaignDto.name } returns NAME
        every { campaignDto.description } returns DESCRIPTION
        every { campaignDto.image } returns imageDto
        every { imageDto.url } returns IMAGE_URL
        coEvery { campaignRepository.getCampaigns() } returns campaignsResponse

        val result = runBlocking {
            getCampaignListUseCase.execute()
        }

        coVerify {
            campaignRepository.getCampaigns()
        }

        val expectedCampaignsList = listOf(Campaign(name = NAME, description = DESCRIPTION, imageUrl = IMAGE_URL))
        Assert.assertEquals(expectedCampaignsList, result)
    }

    @Test
    fun `remove campaign if campaign name is null`() {
        every { campaignsResponse.metadata } returns metaData
        every { metaData.data } returns listOf(campaignDto)
        every { campaignDto.name } returns null
        every { campaignDto.description } returns DESCRIPTION
        every { campaignDto.image } returns imageDto
        every { imageDto.url } returns IMAGE_URL
        coEvery { campaignRepository.getCampaigns() } returns campaignsResponse

        val result = runBlocking {
            getCampaignListUseCase.execute()
        }

        Assert.assertEquals(0, result.size)
    }

    @Test
    fun `remove campaign if campaign name is empty`() {
        every { campaignsResponse.metadata } returns metaData
        every { metaData.data } returns listOf(campaignDto)
        every { campaignDto.name } returns EMPTY
        every { campaignDto.description } returns DESCRIPTION
        every { campaignDto.image } returns imageDto
        every { imageDto.url } returns IMAGE_URL
        coEvery { campaignRepository.getCampaigns() } returns campaignsResponse

        val result = runBlocking {
            getCampaignListUseCase.execute()
        }

        Assert.assertEquals(0, result.size)
    }

    @Test
    fun `remove campaign if description name is null`() {
        every { campaignsResponse.metadata } returns metaData
        every { metaData.data } returns listOf(campaignDto)
        every { campaignDto.name } returns NAME
        every { campaignDto.description } returns null
        every { campaignDto.image } returns imageDto
        every { imageDto.url } returns IMAGE_URL
        coEvery { campaignRepository.getCampaigns() } returns campaignsResponse

        val result = runBlocking {
            getCampaignListUseCase.execute()
        }

        Assert.assertEquals(0, result.size)
    }

    @Test
    fun `remove campaign if campaign description is empty`() {
        every { campaignsResponse.metadata } returns metaData
        every { metaData.data } returns listOf(campaignDto)
        every { campaignDto.name } returns NAME
        every { campaignDto.description } returns EMPTY
        every { campaignDto.image } returns imageDto
        every { imageDto.url } returns IMAGE_URL
        coEvery { campaignRepository.getCampaigns() } returns campaignsResponse

        val result = runBlocking {
            getCampaignListUseCase.execute()
        }

        Assert.assertEquals(0, result.size)
    }*/
}