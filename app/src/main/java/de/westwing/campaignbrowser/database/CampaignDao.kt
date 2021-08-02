package de.westwing.campaignbrowser.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
abstract class CampaignDao {

    @Query("SELECT * FROM CampaignEntity")
    abstract fun getAll(): Flow<List<CampaignEntity>>

    fun getAllDistinctUntilChanged() = getAll().distinctUntilChanged()

    @Query("SELECT * FROM CampaignEntity WHERE uid = (:uid)")
    abstract fun loadById(uid: Int): CampaignEntity

    @Insert
    abstract fun insertAll(list: List<CampaignEntity>)

    @Delete
    abstract fun delete(campaignEntity: CampaignEntity)

    @Query("DELETE FROM CampaignEntity")
    abstract fun deleteAll()
}