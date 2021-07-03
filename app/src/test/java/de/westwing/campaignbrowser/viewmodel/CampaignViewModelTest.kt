package de.westwing.campaignbrowser

import de.westwing.campaignbrowser.model.Campaign
import de.westwing.campaignbrowser.usecase.GetCampaignListUseCase
import de.westwing.campaignbrowser.viewmodel.CampaignViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class CampaignViewModelTest {

    @MockK
    private lateinit var campaign: Campaign

    private lateinit var viewModel: CampaignViewModel

    @MockK
    private lateinit var getCampaignListUseCase: GetCampaignListUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = CampaignViewModel(
            getCampaignListUseCase,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
    }

    @Test
    fun `Test CampaignViewModel here`() {
        every { getCampaignListUseCase.execute() } returns Single.just(listOf(campaign))

        viewModel.getCampaigns()

        val test = viewModel.campaignsData.observeForever {  }
    }
}