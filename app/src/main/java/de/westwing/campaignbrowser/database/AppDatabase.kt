package de.westwing.campaignbrowser.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CampaignEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun campaignDao() : CampaignDao

}