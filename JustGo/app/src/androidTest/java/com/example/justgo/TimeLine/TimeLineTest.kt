package com.example.justgo.TimeLine

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.justgo.R
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TimeLineTest {

    @Test
    fun getTripDates() {
        onView(withId(R.id.date_EditText)).perform(typeText("2020-02-12"))
        onView(withId(R.id.time_EditText)).perform(typeText("12:00"))
        onView(withId(R.id.saveDate_floatActionButton)).perform(click())
    }
}