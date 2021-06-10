package com.example.justgo

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)class CoTravellerActivityTest{

    @Test
    fun addCoTraveller(){
        val coTravellerMenuString = "Co-Travellers"
        val travellerName = "testTraveller"

        val scenario = launchActivity<MainActivity>()
        Espresso.onView(ViewMatchers.withText("Barcelona")).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.single_trip_add_item)).perform(ViewActions.click())
        try{
            Espresso.onView(ViewMatchers.withText(coTravellerMenuString)).perform(ViewActions.click())
        } catch (e: androidx.test.espresso.NoMatchingViewException) {
            Espresso.pressBack()
        }
        Espresso.onView(ViewMatchers.withText(coTravellerMenuString)).perform(ViewActions.click())

        onView(withId(R.id.coTravellers_addButton)).perform(click())
        onView(withId(R.id.CoTravellerName_EditText)).perform(ViewActions.typeText(travellerName))
        closeSoftKeyboard()
        onView(withId(R.id.saveCoTraveller_floatActionButton)).perform(click())
        onView(withText(travellerName)).check(matches(withText(travellerName)))

        onView(allOf(
                hasSibling(withText(travellerName)),
                withId(R.id.delete_co_traveller_button))
        ).perform(click())
    }


    @Test
    fun removeCoTraveller(){
        val coTravellerMenuString = "Co-Travellers"
        val scenario = launchActivity<MainActivity>()
        Espresso.onView(ViewMatchers.withText("Barcelona")).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.single_trip_add_item)).perform(ViewActions.click())
        try{
            Espresso.onView(ViewMatchers.withText(coTravellerMenuString)).perform(ViewActions.click())
        } catch (e: androidx.test.espresso.NoMatchingViewException) {
            Espresso.pressBack()
        }
        Espresso.onView(ViewMatchers.withText(coTravellerMenuString)).perform(ViewActions.click())

        onView(withId(R.id.coTravellers_addButton)).perform(click())
        onView(withId(R.id.CoTravellerName_EditText)).perform(ViewActions.typeText("testTraveller"))
        closeSoftKeyboard()
        onView(withId(R.id.saveCoTraveller_floatActionButton)).perform(click())
        onView(withText("testTraveller")).check(matches(withText("testTraveller")))

        onView(allOf(
                hasSibling(withText("testTraveller")),
                withId(R.id.delete_co_traveller_button))
        ).perform(click())

        onView(withText("testTraveller")).check(doesNotExist())
    }

    @Test
    fun coTraveller_addTask(){
        val coTravellerMenuString = "Co-Travellers"
        val travellerName = "testTraveller"
        val scenario = launchActivity<MainActivity>()
        Espresso.onView(ViewMatchers.withText("Barcelona")).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.single_trip_add_item)).perform(ViewActions.click())
        try{
            Espresso.onView(ViewMatchers.withText(coTravellerMenuString)).perform(ViewActions.click())
        } catch (e: androidx.test.espresso.NoMatchingViewException) {
            Espresso.pressBack()
        }
        Espresso.onView(ViewMatchers.withText(coTravellerMenuString)).perform(ViewActions.click())

        onView(withId(R.id.coTravellers_addButton)).perform(click())
        onView(withId(R.id.CoTravellerName_EditText)).perform(ViewActions.typeText(travellerName))
        closeSoftKeyboard()
        onView(withId(R.id.saveCoTraveller_floatActionButton)).perform(click())
        onView(withText(travellerName)).check(matches(withText(travellerName)))

        onView(allOf(
                withId(R.id.tasks_heading),
                hasSibling(withText(travellerName))
        )).perform(click())

        onView(withHint(CoreMatchers.containsString("Enter new task"))).perform(typeText("test task"))
        onView(withText("OK")).perform(click())

        onView(allOf(
                withId(R.id.task_list),
                hasSibling(withText(travellerName)))
        ).check(matches(withText(containsString("test task"))))

        onView(allOf(
                withParent(withChild(withText(travellerName))),
                withId(R.id.delete_co_traveller_button))
        ).perform(click())
    }
}