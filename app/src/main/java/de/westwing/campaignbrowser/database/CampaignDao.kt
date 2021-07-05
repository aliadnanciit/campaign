package de.westwing.campaignbrowser.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CampaignDao {

    @Query("SELECT * FROM CampaignEntity")
    fun getAll(): List<CampaignEntity>

    @Query("SELECT * FROM CampaignEntity WHERE uid = (:uid)")
    fun loadById(uid: Int): CampaignEntity

    @Insert
    fun insertAll(list: List<CampaignEntity>)

    @Delete
    fun delete(campaignEntity: CampaignEntity)

    @Query("DELETE FROM CampaignEntity")
    fun deleteAll()
}