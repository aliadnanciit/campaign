package de.westwing.campaignbrowser.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.westwing.campaignbrowser.model.Campaign
import de.westwing.campaignbrowser.model.server.CampaignStates
import de.westwing.campaignbrowser.usecase.GetCampaignListUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CampaignViewModelTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @MockK
    private lateinit var campaign: Campaign
    private lateinit var viewModel: CampaignViewModel

    @MockK
    private lateinit var getCampaignListUseCase: GetCampaignListUseCase

    /*@Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = CampaignViewModel(
            getCampaignListUseCase
        )
    }

    @Test
    fun `should show success state`() {
        coEvery { getCampaignListUseCase.execute() } returns flowOf(listOf(campaign))

        viewModel.getCampaigns()

        Assert.assertEquals(
            CampaignStates.Success(listOf(campaign)),
            viewModel.campaignsStateFlow.value
        )
    }*/

    // NEED to fix this scenario as well
    /*@Test
    fun `should show error state`() {
        val throwable: Throwable = RuntimeException()
        coEvery { getCampaignListUseCase.execute() } throws throwable

        viewModel.getCampaigns()

        Assert.assertEquals(
            CampaignStates.Error(throwable),
            viewModel.campaignsStateFlow.value
        )
    }*/
}