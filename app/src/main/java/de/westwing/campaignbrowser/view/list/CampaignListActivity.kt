package de.westwing.campaignbrowser.view.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import de.westwing.campaignbrowser.R
import de.westwing.campaignbrowser.common.DensityConverter
import de.westwing.campaignbrowser.common.ItemHorizontalSpaceDecoration
import de.westwing.campaignbrowser.common.ItemVerticalSpaceDecoration
import de.westwing.campaignbrowser.databinding.ActivityCampaignListBinding
import de.westwing.campaignbrowser.model.Campaign
import de.westwing.campaignbrowser.model.server.CampaignStates
import de.westwing.campaignbrowser.test.Yes
import de.westwing.campaignbrowser.view.detail.CampaignDetailActivity
import de.westwing.campaignbrowser.viewmodel.CampaignViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class CampaignListActivity : AppCompatActivity(), CampaignClickListener {

    private val viewModel: CampaignViewModel by viewModels()

    @Inject
    lateinit var doYes: Yes

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


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.campaignsStateFlow.collect { campaigns ->
                    processViewState(campaigns)
                }
            }
        }
        loadCampaignsData()

        Log.i("doYes", doYes.hello())

    }

    private fun processViewState(campaignState: CampaignStates) {
        binding.loadingIndicator.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE
        binding.noContent.visibility = View.GONE
        binding.campaignsRecycler.visibility = View.GONE
        when(campaignState) {
            is CampaignStates.Loading -> {
                binding.loadingIndicator.visibility = View.VISIBLE
            }
            is CampaignStates.NoContent -> {
                binding.noContent.visibility = View.VISIBLE
            }
            is CampaignStates.Success -> {
                binding.campaignsRecycler.visibility = View.VISIBLE
                showDate(campaignState.list)
            }
            is CampaignStates.Error -> {
                binding.errorContainer.visibility = View.VISIBLE
            }
        }
    }

    private fun loadCampaignsData() {
        binding.loadingIndicator.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE
        viewModel.fetchCampaigns()
    }

    private fun showDate(list: List<Campaign>) {
        adapter.submitList(list)
    }

    override fun onclick(campaign: Campaign) {
        CampaignDetailActivity.startDetailActivity(this, campaign)
    }
}