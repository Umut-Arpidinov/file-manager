package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import kg.o.internlabs.omarket.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistrationFragmentTest {
    private lateinit var fragmentScenario: FragmentScenario<RegistrationFragment>


    @Before
    fun setUp() {
        fragmentScenario = launchFragmentInContainer()
        fragmentScenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun testRegistration() {
        onView(withId(R.id.cus_num)).perform(typeText("504772077"))
        onView(withId(R.id.cus_pass)).perform(typeText("12345678At"))
        onView(withId(R.id.cus_pass1)).perform(typeText("12345678At"))
        onView(withId(R.id.btn_send_otp)).check(matches(isClickable()))
    }
}