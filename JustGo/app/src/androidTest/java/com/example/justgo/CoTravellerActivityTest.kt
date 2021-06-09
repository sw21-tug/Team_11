package com.example.justgo

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)class CoTravellerActivityTest{

    @Test
    fun addCoTraveller(){
        val coTravellerMenuString = "Co-Travellers"
        val scenario = launchActivity<MainActivity>()
        Espresso.onView(ViewMatchers.withText("Barcelona")).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.trip_title)).check(ViewAssertions.matches(ViewMatchers.withText("Barcelona")))

        Espresso.onView(ViewMatchers.withId(R.id.single_trip_add_item)).perform(ViewActions.click())
        try{
            Espresso.onView(ViewMatchers.withText(coTravellerMenuString)).perform(ViewActions.click()) // Add dates item
        } catch (e: androidx.test.espresso.PerformException) {
            Espresso.pressBack() // Dates already added
        }
        Espresso.onView(ViewMatchers.withText(coTravellerMenuString)).perform(ViewActions.click()) // Go into dates

        onView(withId(R.id.coTravellers_addButton)).perform(click())
        onView(withId(R.id.CoTravellerName_EditText)).perform(ViewActions.typeText("testTraveller"))
        closeSoftKeyboard()
        onView(withId(R.id.saveCoTraveller_floatActionButton)).perform(click())
    }


    @Test
    fun removeCoTraveller(){
        val coTravellerMenuString = "Co-Travellers"
        val scenario = launchActivity<MainActivity>()
        Espresso.onView(ViewMatchers.withText("Barcelona")).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.trip_title)).check(ViewAssertions.matches(ViewMatchers.withText("Barcelona")))

        Espresso.onView(ViewMatchers.withId(R.id.single_trip_add_item)).perform(ViewActions.click())
        try{
            Espresso.onView(ViewMatchers.withText(coTravellerMenuString)).perform(ViewActions.click()) // Add dates item
        } catch (e: androidx.test.espresso.PerformException) {
            Espresso.pressBack() // Dates already added
        }
        Espresso.onView(ViewMatchers.withText(coTravellerMenuString)).perform(ViewActions.click()) // Go into dates

        onView(withId(R.id.coTravellers_addButton)).perform(click())
        onView(withId(R.id.CoTravellerName_EditText)).perform(ViewActions.typeText("testTraveller"))
        closeSoftKeyboard()
        onView(withId(R.id.saveCoTraveller_floatActionButton)).perform(click())

        onView(withId(R.id.delete_co_traveller_button)).perform(click())
    }
}