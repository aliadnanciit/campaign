package de.westwing.campaignbrowser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.westwing.campaignbrowser.model.CampaignStates
import de.westwing.campaignbrowser.model.Campaign
import de.westwing.campaignbrowser.usecase.GetCampaignListUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

class CampaignViewModel @Inject constructor(
    private val getCampaignListUseCase: GetCampaignListUseCase,
    @Named("IO_SCHEDULER") private val io: Scheduler,
) : ViewModel() {

    val campaignsData = MutableLiveData<CampaignStates>()

    fun getCampaigns() {
        campaignsData.value = CampaignStates.Loading
        getCampaignListUseCase.execute()
            .subscribeOn(io)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onSuccess, this::onError)
    }

    private fun onSuccess(list: List<Campaign>) {
        campaignsData.value = CampaignStates.Success(list)
    }

    private fun onError(throwable: Throwable) {
        campaignsData.value = CampaignStates.Error(throwable)
        throwable.printStackTrace()
    }
}