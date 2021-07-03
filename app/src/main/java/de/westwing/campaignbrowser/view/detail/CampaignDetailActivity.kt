package de.westwing.campaignbrowser.view.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import de.westwing.campaignbrowser.databinding.ActivityCampaignDetailBinding
import de.westwing.campaignbrowser.model.Campaign

const val ACTIVE_CAMPAIGN = "active_campaign"

class CampaignDetailActivity : AppCompatActivity() {

    companion object {
        fun startDetailActivity(activity: AppCompatActivity, campaign: Campaign) {
            val intent = Intent(activity, CampaignDetailActivity::class.java)
            intent.putExtra(ACTIVE_CAMPAIGN, campaign)
            activity.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityCampaignDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCampaignDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtra<Campaign>(ACTIVE_CAMPAIGN)?.let {
            binding.campaignName.text = it.name
            binding.campaignDescription.text = it.description
            Glide.with(this)
                .load(it.imageUrl)
                .into(binding.campaignImage)
        }
    }
}