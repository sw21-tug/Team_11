package com.example.justgo

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FoodsActivityTest{

    @Test
    fun addFood() {
        val foodMenuString = "Foods"
        val foodName = "test food"

        val scenario = launchActivity<MainActivity>()
        Espresso.onView(ViewMatchers.withText("Barcelona")).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.single_trip_add_item)).perform(ViewActions.click())
        try{
            Espresso.onView(ViewMatchers.withText(foodMenuString)).perform(ViewActions.click())
        } catch (e: androidx.test.espresso.NoMatchingViewException) {
            Espresso.pressBack()
        }
        Espresso.onView(ViewMatchers.withText(foodMenuString)).perform(ViewActions.click())

        onView(withId(R.id.add_food_button)).perform(click())
        onView(withId(R.id.foodName_EditText)).perform(ViewActions.typeText(foodName))
        onView(withId(R.id.foodLocation_EditText)).perform(ViewActions.typeText("test loc"))
        closeSoftKeyboard()
        onView(withId(R.id.saveFood_floatActionButton)).perform(click())
        onView(withText(foodName)).check(matches(withText(foodName)))

        onView(withText(foodName)).perform(swipeRight())
    }

    @Test
    fun removeFood(){
        val foodMenuString = "Foods"
        val foodName = "test food"

        val scenario = launchActivity<MainActivity>()
        Espresso.onView(ViewMatchers.withText("Barcelona")).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.single_trip_add_item)).perform(ViewActions.click())
        try{
            Espresso.onView(ViewMatchers.withText(foodMenuString)).perform(ViewActions.click())
        } catch (e: androidx.test.espresso.NoMatchingViewException) {
            Espresso.pressBack()
        }
        Espresso.onView(ViewMatchers.withText(foodMenuString)).perform(ViewActions.click())

        onView(withId(R.id.add_food_button)).perform(click())
        onView(withId(R.id.foodName_EditText)).perform(ViewActions.typeText(foodName))
        onView(withId(R.id.foodLocation_EditText)).perform(ViewActions.typeText("test loc"))
        closeSoftKeyboard()
        onView(withId(R.id.saveFood_floatActionButton)).perform(click())
        onView(withText(foodName)).check(matches(withText(foodName)))

        onView(withText(foodName)).perform(swipeRight())

        onView(withText(foodName)).check(doesNotExist())
    }
}