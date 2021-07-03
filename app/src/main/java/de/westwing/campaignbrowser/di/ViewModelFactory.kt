package de.westwing.campaignbrowser.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Lazy
import javax.inject.Inject

class ViewModelFactory<VM : ViewModel> @Inject constructor(
    private val vieModel: Lazy<VM>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  vieModel.get() as T
    }
}
