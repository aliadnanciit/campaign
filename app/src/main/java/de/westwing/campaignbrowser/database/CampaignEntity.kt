package de.westwing.campaignbrowser.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CampaignEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val name: String,
    val description: String,
    val imageUrl: String
)
