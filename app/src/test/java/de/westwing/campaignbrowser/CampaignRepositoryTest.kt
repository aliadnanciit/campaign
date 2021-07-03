package de.westwing.campaignbrowser

import de.westwing.campaignbrowser.service.ApiInterface
import de.westwing.campaignbrowser.repository.CampaignRepositoryImpl
import de.westwing.campaignbrowser.repository.CampaignRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class CampaignRepositoryTest {

    @Mock
    private lateinit var apiInterface: ApiInterface

    private lateinit var repository: CampaignRepository

    @Before
    fun setup() {
        repository = CampaignRepositoryImpl(apiInterface)
    }

    @Test
    fun `CampaignRepository returns non-empty list of campaigns`() {
        val observer = repository.getCampaigns().test()

        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertValue {
            it.isNotEmpty()
        }
    }
}