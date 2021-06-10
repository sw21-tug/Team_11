package com.example.justgo

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.anything
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest{

    @Test
    fun testOpenMyTrips() {
        val scenario = launchActivity<MainActivity>()
        onView(withId(R.id.my_trips_button)).perform(ViewActions.click())

        onView(withId(R.id.list_view_description)).check(matches(withText("MY TRIPS")))

    }


    @Test
    fun testOpenSampleTrip() {
        val scenario = launchActivity<MainActivity>()
        onView(withText("Barcelona")).perform(ViewActions.click());

        onView(withId(R.id.trip_title)).check(matches(withText("Barcelona")))
    }


    @Test
    fun addTrip(){
        val tripTitle = "testTrip"
        val scenario = launchActivity<MainActivity>()
        onView(withId(R.id.createtripFloatingActionButton)).perform(ViewActions.click())

        onView(withId(R.id.tripName_EditText)).perform(ViewActions.typeText(tripTitle))
        closeSoftKeyboard();
        onView(withId(R.id.saveTrip_floatActionButton)).perform(ViewActions.click())

        onView(withId(R.id.my_trips_button)).perform(ViewActions.click())

        // Makes sure the trip with title <tripTitle> is added
        onData(anything()).inAdapterView(withId(R.id.list_view_of_trips)).atPosition(0).check(matches(withText(tripTitle)))
    }
}