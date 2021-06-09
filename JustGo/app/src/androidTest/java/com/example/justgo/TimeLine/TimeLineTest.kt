package com.example.justgo.TimeLine

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.justgo.MainActivity
import com.example.justgo.R
import org.hamcrest.CoreMatchers.containsString
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TimeLineTest {

    @Test
    fun getTripDates() {
        val scenario = launchActivity<MainActivity>()
        onView(ViewMatchers.withText("Barcelona")).perform(ViewActions.click());

        onView(withId(R.id.trip_title)).check(ViewAssertions.matches(ViewMatchers.withText("Barcelona")))

        onView(withId(R.id.single_trip_add_item)).perform(ViewActions.click())
        try{
            onView(withText("Dates")).perform(ViewActions.click()) // Add dates item
        } catch (e: androidx.test.espresso.NoMatchingViewException) {
            pressBack() // Dates already added
        }
        onView(withText("Dates")).perform(ViewActions.click()) // Go into dates
        onView(withId(R.id.add_date_button)).perform(ViewActions.click())

        onView(withId(R.id.date_EditText)).perform(clearText())
        onView(withId(R.id.date_EditText)).perform(typeText("2020-02-12"))

        onView(withId(R.id.time_EditText)).perform(clearText())
        onView(withId(R.id.time_EditText)).perform(typeText("12:00"))

        onView(withId(R.id.date_description_EditText)).perform(typeText("testDate"))    

        closeSoftKeyboard()
        onView(withId(R.id.saveDate_floatActionButton)).perform(ViewActions.click())

        onView(withText(containsString("testDate"))).check(matches(withText(containsString("testDate"))))
    }
}