package app.ibiocd.appointment.endToEnd

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.ibiocd.appointment.auth.navegation.SessionDestinationsActivity
import app.ibiocd.appointment.auth.view.SessionActivity
import app.ibiocd.appointment.auth.view.SessionNavHost
import app.ibiocd.appointment.util.AppointmentConstant
import app.ibiocd.appointment.util.Constants
import app.ibiocd.appointment.util.HomeConstants
import app.ibiocd.appointment.util.SessionScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SignInToHomeInstrumentedTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<SessionActivity>()
    lateinit var navController: TestNavHostController

    @Test
    fun testPatientLoginAndAppointmentCreation() {

        initializeAppAndNavigateToLogin()

        performPatientLogin()

        createNewMedicalAppointment()

        searchAndScheduleMedicalAppointment()
    }

    private fun initializeAppAndNavigateToLogin() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SessionNavHost(navController, SessionDestinationsActivity.Beginning.route, Constants.TOKEN)
        }
        composeTestRule.apply {
            onNodeWithText(SessionScreen.COMP_LOGIN).performClick()
            waitForIdle() // Esperar a que se complete la interacción
        }

    }

    private fun performPatientLogin() {
        composeTestRule.apply {
            onNodeWithText(Constants.PATIENT).performClick()
        }
        fillForm()

    }

    private fun createNewMedicalAppointment() {

        composeTestRule.apply {
            waitUntilExists(hasTestTag(HomeConstants.TAG_CARD_NEW_APPOINTMENT))
            onNodeWithTag(HomeConstants.TAG_BTN_NEW_APPOINTMENT)
                .performClick()

        }

    }

    private val WAIT_UNTIL_TIMEOUT = 1_000L

    fun ComposeContentTestRule.waitUntilNodeCount(
        matcher: SemanticsMatcher,
        count: Int,
        timeoutMillis: Long = WAIT_UNTIL_TIMEOUT
    ) {
        waitUntil(timeoutMillis) {
            onAllNodes(matcher).fetchSemanticsNodes().size == count
        }
    }

    fun ComposeContentTestRule.waitUntilExists(
        matcher: SemanticsMatcher,
        timeoutMillis: Long = WAIT_UNTIL_TIMEOUT
    ) = waitUntilNodeCount(matcher, 1, timeoutMillis)

    fun ComposeContentTestRule.waitUntilDoesNotExist(
        matcher: SemanticsMatcher,
        timeoutMillis: Long = WAIT_UNTIL_TIMEOUT
    ) = waitUntilNodeCount(matcher, 0, timeoutMillis)

    private fun searchAndScheduleMedicalAppointment() {
        composeTestRule.apply {
            onNodeWithText(Constants.MEDICAL).performClick()
            onNodeWithContentDescription(AppointmentConstant.DESCRIPTION_LIST_MEDICAL).performClick()
            onNodeWithText(Constants.MEDICAL_NAME).performClick()
            onNodeWithContentDescription(AppointmentConstant.DESCRIPTION_LIST_PROFESSION).performClick()
            onNodeWithText(Constants.PROFESSION).performClick()
            onNodeWithContentDescription(AppointmentConstant.DESCRIPTION_LIST_SPECIALITY).performClick()
            onNodeWithText(Constants.SPECIALTY).performClick()
            onNodeWithContentDescription(AppointmentConstant.DESCRIPTION_LIST_PATIENT).performClick()
            onNodeWithText(Constants.PATIENT_NAME).performClick()
            onNodeWithContentDescription(AppointmentConstant.DESCRIPTION_BTN_CALENDAR).performClick()
            onNodeWithText(Constants.SEARCH).performClick()
            onNodeWithText(Constants.HOUR).performClick()
            onNodeWithText(Constants.CONFIRM).performClick()

        }
    }

    fun fillForm() {
        composeTestRule.apply {
            onNodeWithTag(SessionScreen.TAG_TEXT_USER).performTextInput(Constants.USER_EMAIL)
            onNodeWithTag(SessionScreen.TAG_TEXT_USER).assertTextContains(Constants.USER_EMAIL)
            onNodeWithTag(SessionScreen.TAG_TEXT_PASSWORD).performTextInput(Constants.USER_PASSWORD)
            onNodeWithContentDescription(SessionScreen.DESCRIPTION_IC_OFF_PASS).assertExists()
            onNodeWithTag(SessionScreen.TAG_IC_PASSWORD).performClick()
            onNodeWithContentDescription(SessionScreen.DESCRIPTION_IC_ON_PASS).assertExists()
            onNodeWithTag(SessionScreen.TAG_TEXT_PASSWORD).assertTextContains(Constants.USER_PASSWORD)
            onNodeWithText(Constants.CONTINUE).performClick()

        }

    }


}