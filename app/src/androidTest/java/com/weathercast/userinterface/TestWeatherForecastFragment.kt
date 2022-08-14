package com.weathercast.userinterface


import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.weathercast.BaseUiTest
import com.weathercast.R
import com.weathercast.mockapi.MockApiDataHelper.mockSuccessResponse
import com.weathercast.mockapi.MockApiResponseDispatcher
import com.weathercast.util.convertToReadableDateTime
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


private const val API_CALL_FOR_LONDON_WEATHER =
    "/data/2.5/forecast?&q=London&appid=ee76fbb294ea96f735ba6c1276ca5d7c&units=metric"

@RunWith(AndroidJUnit4ClassRunner::class)
class TestWeatherForecastFragment : BaseUiTest() {
    @Before
    override fun setup() {
        super.setup()
    }

    @Test
    fun verify_weather_forecast_for_success_response() {
        setMockApiDispatcher(MockApiResponseDispatcher(mockWeatherForecastSuccess()))
        activityRule.launchActivity(null)

        // verify temperature data
        onView(withId(R.id.rvWeatherForecast))
            .check(
                matches(
                    atPositionOnView(
                        0,
                        withText(context.getString(R.string.temperature_text, "27.35", "27.13")),
                        R.id.tvTemperature
                    )
                )
            )

        // verify weather description
        onView(withId(R.id.rvWeatherForecast))
            .check(
                matches(
                    atPositionOnView(
                        0,
                        withText("Moderate rain"),
                        R.id.tvWeatherDescription
                    )
                )
            )

        // verify date time
        onView(withId(R.id.rvWeatherForecast))
            .check(
                matches(
                    atPositionOnView(
                        0,
                        withText("2021-09-12 15:00:00".convertToReadableDateTime()),
                        R.id.tvTime
                    )
                )
            )
    }

    private fun mockWeatherForecastSuccess(): Map<String, MockResponse> {
        return mapOf(
            API_CALL_FOR_LONDON_WEATHER to
                    mockSuccessResponse("weather_forecast_success.json")
        )
    }
}

fun atPositionOnView(
    position: Int, itemMatcher: Matcher<View?>,
    @NonNull targetViewId: Int
): Matcher<View?> {
    return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) { }

        override fun matchesSafely(recyclerView: RecyclerView): Boolean {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
            val targetView: View = viewHolder!!.itemView.findViewById(targetViewId)
            return itemMatcher.matches(targetView)
        }
    }
}