package de.westwing.campaignbrowser.view.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import de.westwing.campaignbrowser.R
import de.westwing.campaignbrowser.common.DensityConverter
import de.westwing.campaignbrowser.common.ItemHorizontalSpaceDecoration
import de.westwing.campaignbrowser.common.ItemVerticalSpaceDecoration
import de.westwing.campaignbrowser.databinding.ActivityCampaignListBinding
import de.westwing.campaignbrowser.di.ViewModelFactory
import de.westwing.campaignbrowser.model.Campaign
import de.westwing.campaignbrowser.model.server.CampaignStates
import de.westwing.campaignbrowser.view.detail.CampaignDetailActivity
import de.westwing.campaignbrowser.viewmodel.CampaignViewModel
import javax.inject.Inject

class CampaignListActivity : AppCompatActivity(), CampaignClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<CampaignViewModel>
    private lateinit var viewModel: CampaignViewModel

    lateinit var binding: ActivityCampaignListBinding
    private lateinit var adapter : CampaignListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityCampaignListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.retry.setOnClickListener {
            loadCampaignsData()
        }
        adapter = CampaignListAdapter(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CampaignViewModel::class.java)
        binding.campaignsRecycler.adapter = adapter
        binding.campaignsRecycler.addItemDecoration(
            ItemVerticalSpaceDecoration(
                DensityConverter.toPixel(
                    resources, resources.getInteger(R.integer.campaign_list_item_vertical_spacing)
                )
            )
        )
        binding.campaignsRecycler.addItemDecoration(
            ItemHorizontalSpaceDecoration(
                DensityConverter.toPixel(
                    resources, resources.getInteger(R.integer.campaign_list_item_horizontal_spacing)
                )
            )
        )


        viewModel.campaignsData.observe(this, {
                campaigns -> processViewState(campaigns)
        })
        loadCampaignsData()
    }

    private fun processViewState(campaignState: CampaignStates) {
        when(campaignState) {
            is CampaignStates.Loading -> {
                binding.loadingIndicator.visibility = View.VISIBLE
            }
            is CampaignStates.Success -> {
                binding.loadingIndicator.visibility = View.GONE
                binding.errorContainer.visibility = View.GONE
                showDate(campaignState.list)
            }
            is CampaignStates.Error -> {
                showError()
            }
        }
    }

    private fun loadCampaignsData() {
        viewModel.getCampaigns()
    }

    private fun showDate(list: List<Campaign>) {
        binding.campaignsRecycler.visibility = View.VISIBLE
        adapter.submitList(list)
    }

    private fun showError() {
        binding.campaignsRecycler.visibility = View.GONE
        binding.loadingIndicator.visibility = View.GONE
        binding.errorContainer.visibility = View.VISIBLE
    }

    override fun onclick(campaign: Campaign) {
        val intent = Intent(this, CampaignDetailActivity::class.java)
        startActivity(intent)
    }
}