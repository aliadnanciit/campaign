package de.westwing.campaignbrowser.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import de.westwing.campaignbrowser.databinding.ActivityCampaignDetailBinding

class CampaignDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityCampaignDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCampaignDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.campaignName.text = "Test 1"
        binding.campaignDescription.text = "Test Detail 10"
        Glide.with(this)
            .load("https://cdn-ww.westwing.com/image/upload/v1623334571/club/de/campaign/DESHIN5/original_43")
            .into(binding.campaignImage)
    }
}