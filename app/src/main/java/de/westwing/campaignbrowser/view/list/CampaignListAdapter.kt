package de.westwing.campaignbrowser.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.westwing.campaignbrowser.databinding.ItemCampaignBinding
import de.westwing.campaignbrowser.model.Campaign

class CampaignListAdapter(
    private val campaignClickListener: CampaignClickListener
): ListAdapter<Campaign, CampaignViewHolder>(campaignDiff) {

    companion object {
        val campaignDiff = object: DiffUtil.ItemCallback<Campaign>() {
            override fun areItemsTheSame(oldItem: Campaign, newItem: Campaign) = oldItem == newItem
            override fun areContentsTheSame(oldItem: Campaign, newItem: Campaign) = oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignViewHolder {
        val holder = CampaignViewHolder(
            ItemCampaignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        holder.binding.root.setOnClickListener {
            val campaign = it.tag as Campaign
            campaignClickListener.onclick(campaign)
        }
        return holder
    }

    override fun onBindViewHolder(holder: CampaignViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CampaignViewHolder(internal val binding: ItemCampaignBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(campaign: Campaign) {
        binding.root.tag = campaign
        binding.campaignName.text = campaign.name
        binding.campaignDescription.text = campaign.description
        Glide.with(binding.campaignImage.context)
            .load(campaign.imageUrl)
            .into(binding.campaignImage)
    }
}