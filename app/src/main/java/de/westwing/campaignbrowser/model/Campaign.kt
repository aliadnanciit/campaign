package de.westwing.campaignbrowser.model

data class Campaign(
    val name: String,
    val description: String,
    val imageUrl: String = ""
)