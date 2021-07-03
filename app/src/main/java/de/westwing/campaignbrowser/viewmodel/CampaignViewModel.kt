package de.westwing.campaignbrowser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.westwing.campaignbrowser.model.Campaign
import de.westwing.campaignbrowser.model.server.CampaignStates
import de.westwing.campaignbrowser.usecase.GetCampaignListUseCase
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class CampaignViewModel @Inject constructor(
    private val getCampaignListUseCase: GetCampaignListUseCase,
    @Named("IO_SCHEDULER") private val ioScheduler: Scheduler,
    @Named("MAIN_SCHEDULER") private val mainScheduler: Scheduler
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val campaignsData = MutableLiveData<CampaignStates>()

    fun getCampaigns() {
        campaignsData.value = CampaignStates.Loading

        compositeDisposable.add(
            getCampaignListUseCase.execute()
            .subscribeOn(ioScheduler)
            .observeOn(mainScheduler)
            .subscribe(this::onSuccess, this::onError)
        )
    }

    private fun onSuccess(list: List<Campaign>) {
        campaignsData.postValue(CampaignStates.Success(list))
    }

    private fun onError(throwable: Throwable) {
        campaignsData.value = CampaignStates.Error(throwable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}