package de.westwing.campaignbrowser.view.list

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import de.westwing.campaignbrowser.R
import de.westwing.campaignbrowser.common.DensityConverter
import de.westwing.campaignbrowser.common.ItemHorizontalSpaceDecoration
import de.westwing.campaignbrowser.common.ItemVerticalSpaceDecoration
import de.westwing.campaignbrowser.databinding.ActivityCampaignListBinding
import de.westwing.campaignbrowser.model.Campaign
import de.westwing.campaignbrowser.model.server.CampaignStates
import de.westwing.campaignbrowser.view.detail.CampaignDetailActivity
import de.westwing.campaignbrowser.viewmodel.CampaignViewModel

@AndroidEntryPoint
class CampaignListActivity : AppCompatActivity(), CampaignClickListener {

    private val viewModel: CampaignViewModel by viewModels()

    lateinit var binding: ActivityCampaignListBinding
    private lateinit var adapter : CampaignListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCampaignListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.retry.setOnClickListener {
            loadCampaignsData()
        }
        adapter = CampaignListAdapter(this)

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


        viewModel.campaignsLiveData.observe(this, {
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
        binding.loadingIndicator.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE
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
        CampaignDetailActivity.startDetailActivity(this, campaign)
    }
}