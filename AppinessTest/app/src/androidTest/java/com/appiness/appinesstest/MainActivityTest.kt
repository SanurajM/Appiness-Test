package com.appiness.appinesstest

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.rule.ActivityTestRule
import com.appiness.appinesstest.ui.MainActivity
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule<MainActivity>(
        MainActivity::class.java,
        true,
        true
    )

    @Test
    fun testSampleRecyclerVisible() {
        Espresso.onView(ViewMatchers.withId(R.id.rvBooks))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun testCaseForRecyclerScroll() {
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.rvBooks)
        val itemCount = recyclerView.adapter!!.itemCount
        Espresso.onView(ViewMatchers.withId(R.id.rvBooks))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1))
    }

    @Test
    fun testCaseForProgressbarShown() {
        Espresso.onView(ViewMatchers.withId(R.id.smoothProgressBar))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

}
