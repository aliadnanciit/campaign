package de.westwing.campaignbrowser

import android.os.Build
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.westwing.campaignbrowser.model.Campaign
import de.westwing.campaignbrowser.view.list.CampaignListActivity
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class CampaignListActivityTest {

    private val testCampaigns = listOf(
        Campaign("name", "description"),
        Campaign("name2", "description2")
    )

    @get:Rule var activityScenarioRule = activityScenarioRule<CampaignListActivity>()

    @Test
    fun `Give me meaningful name`() {
        val scenario = activityScenarioRule.scenario

        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onActivity {
//            it.processViewState(testCampaigns)
        }

        shadowOf(Looper.getMainLooper()).idle()

//        scenario.onActivity {
//            assertTrue(it.binding.campaignsRecycler.isVisible)
//            assertTrue(it.binding.loadingIndicator.isGone)
//
//            val holder = it.binding.campaignsRecycler.findViewHolderForAdapterPosition(0) as CampaignViewHolder
//            assertEquals(testCampaigns.first().description, holder.binding.campaignDescription.text.toString())
//        }

        scenario.close()
    }
}