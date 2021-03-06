package de.westwing.campaignbrowser.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.westwing.campaignbrowser.model.Campaign
import de.westwing.campaignbrowser.model.server.CampaignStates
import de.westwing.campaignbrowser.usecase.GetCampaignListUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert.assertEquals
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

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = CampaignViewModel(
            getCampaignListUseCase,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
    }

    @Test
    fun `should show success state`() {
        every { getCampaignListUseCase.execute() } returns Single.just(listOf(campaign))

        viewModel.getCampaigns()

        assertEquals(CampaignStates.Success(listOf(campaign)), viewModel.campaignsData.value)
    }
}