package app.ibiocd.appointment.profile

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.ibiocd.appointment.auth.view.SessionActivity
import app.ibiocd.appointment.auth.view.SessionNavHost
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ProfileInstrumentedTest {

        @get:Rule(order  =1)
        var hiltTestRule= HiltAndroidRule(this)

        @get:Rule(order  =2)
        var composeTestRule= createAndroidComposeRule<SessionActivity>()
        lateinit var navController: TestNavHostController

    @Test
    fun patient_LogIn_Error_User(){
        val user="adrianherrera.r.egmail.com"
        val pass="dexter"
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SessionNavHost(navController,"Beginning/?newText=","")
        }

        composeTestRule.onNodeWithText("Iniciar sesion").performClick()
        composeTestRule.onNodeWithText("PACIENTE").performClick()
        form(user,pass)
        composeTestRule.onNodeWithTag("user_error_session").assertTextContains("Error: Ingrese un correo valido")

    }

    @Test
    fun patient_LogIn(){
        val user="adrianherrera.r.e@gmail.com"
        val pass="dexter"
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SessionNavHost(navController,"Beginning/?newText=","")
        }

        composeTestRule.onNodeWithText("Iniciar sesion").performClick()
        composeTestRule.onNodeWithText("PACIENTE").performClick()
        form(user,pass)

    }


    fun form(user:String, pass:String){
        composeTestRule.onNodeWithTag("user_session").performTextInput(user)
        composeTestRule.onNodeWithTag("user_session").assertTextContains(user)
        composeTestRule.onNodeWithTag("pass_session").performTextInput(pass)
        composeTestRule.onNodeWithContentDescription("Ocultar contraseña").performClick()
        composeTestRule.onNodeWithTag("pass_session").assertTextContains(pass)
        composeTestRule.onNodeWithText("Continuar").performClick()

    }

}