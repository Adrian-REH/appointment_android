package app.ibiocd.appointment.Session

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.ibiocd.appointment.auth.view.SessionActivity
import app.ibiocd.appointment.auth.view.SessionNavHost
import com.nopalsoft.simple.rest.presentation.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 */

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SignUpInstrumentedTest {
    @get:Rule(order  =1)
    var hiltTestRule=HiltAndroidRule(this)
    @get:Rule(order  =2)
    var composeTestRule= createAndroidComposeRule<SessionActivity>()
    lateinit var navController: TestNavHostController

    @Test
    fun Patient_CheckIn(){
        val name="Adrian Herrera"
        val dni="42121008"
        val correo="AdrianHerrera.r.e@gmail.com"
        val pass="dexter"
        val confpass="dexter"
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SessionNavHost(navController,"Beginning/?newText=","")
        }

        composeTestRule.onNodeWithText("Registrarse").performClick()
        composeTestRule.onNodeWithText("PACIENTE").performClick()
        patient_Form_test(name,dni,correo,pass,confpass)
    }
    @Test
    fun Patient_CheckIn_Email_Error(){
        val name="Adrian Herrera"
        val dni="42121008"
        val correo="AdrianHerrera.r.egmail.com"
        val pass="dexter"
        val confpass="dexter"

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SessionNavHost(navController,"Beginning/?newText=","")
        }
        composeTestRule.onNodeWithText("Registrarse").performClick()
        composeTestRule.onNodeWithText("PACIENTE").performClick()
        patient_Form_test(name,dni,correo,pass,confpass)
        composeTestRule.onNodeWithTag("patient_email_error").assertTextContains("Error: Ingrese un correo valido")

    }
    @Test
    fun Patient_CheckIn_Password_Error(){
        val name="Adrian Herrera"
        val dni="42121008"
        val correo="AdrianHerrera.r.e@gmail.com"
        val pass="dexter"
        val confpass="d"

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SessionNavHost(navController,"Beginning/?newText=","")
        }
        composeTestRule.onNodeWithText("Registrarse").performClick()
        composeTestRule.onNodeWithText("PACIENTE").performClick()
        patient_Form_test(name,dni,correo,pass,confpass)
        composeTestRule.onNodeWithTag("patient_confirm_pass_error").assertTextContains("Error las contraseñas no coinciden")

    }
    @Test
    fun Patient_CheckIn_Null_Error(){
        val name=""
        val dni=""
        val correo=""
        val pass=""
        val confpass=""

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SessionNavHost(navController,"Beginning/?newText=","")
        }
        composeTestRule.onNodeWithText("Registrarse").performClick()
        composeTestRule.onNodeWithText("PACIENTE").performClick()
        patient_Form_test(name,dni,correo,pass,confpass)


    }

    fun patient_Form_test(name:String, dni:String, correo:String, pass:String, confpass:String){
        composeTestRule.onNodeWithTag("Name_patient").performTextInput(name)
        composeTestRule.onNodeWithTag("Name_patient").assertTextContains(name)
        composeTestRule.onNodeWithTag("Dni_patient").performTextInput(dni)
        composeTestRule.onNodeWithTag("Dni_patient").assertTextContains(dni)
        composeTestRule.onNodeWithTag("Correo_patient").performTextInput(correo)
        composeTestRule.onNodeWithTag("Correo_patient").assertTextContains(correo)
        composeTestRule.onNodeWithTag("Pass_patient").performTextInput(pass)
        composeTestRule.onNodeWithContentDescription("Ocultar contraseña patients").performClick()
        composeTestRule.onNodeWithTag("Pass_patient").assertTextContains(pass)
        composeTestRule.onNodeWithTag("ConfPass_patient").performTextInput(confpass)
        composeTestRule.onNodeWithTag("ConfPass_patient").assertTextContains(confpass)
        composeTestRule.onNodeWithText("Confirmar paciente").performClick()

    }

    @Test
    fun Medical_CheckIn(){
        val name="Elias Herrera"
        val dni="11212118"
        val tuit="44522"
        val correo="AdrianHerrera.r.e@gmail.com"
        val pass="dexter"
        val confpass="dexter"
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SessionNavHost(navController,"Beginning/?newText=","")
        }

        composeTestRule.onNodeWithText("Registrarse").performClick()
        composeTestRule.onNodeWithText("MEDICO").performClick()
        medical_Form_test(name,dni,correo,pass,confpass,tuit)

    }

    @Test
    fun Medical_CheckIn_Email_Error(){
        val name="Elias Herrera"
        val dni="11212118"
        val tuit="44522"
        val correo="AdrianHerrera.r.egmail.com"
        val pass="dexter"
        val confpass="dexter"
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SessionNavHost(navController,"Beginning/?newText=","")
        }
        composeTestRule.onNodeWithText("Registrarse").performClick()
        composeTestRule.onNodeWithText("MEDICO").performClick()
        medical_Form_test(name,dni,correo,pass,confpass,tuit)
        composeTestRule.onNodeWithTag("medical_email_error").assertTextContains("Error: Ingrese un correo valido")

    }

    @Test
    fun Medical_CheckIn_Password_Error(){
        val name="Elias Herrera"
        val dni="11212118"
        val tuit="44522"
        val correo="AdrianHerrera.r.e@gmail.com"
        val pass="dexter"
        val confpass="der"
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SessionNavHost(navController,"Beginning/?newText=","")
        }

        composeTestRule.onNodeWithText("Registrarse").performClick()
        composeTestRule.onNodeWithText("MEDICO").performClick()
        medical_Form_test(name,dni,correo,pass,confpass,tuit)
        composeTestRule.onNodeWithTag("medical_confirm_pass_error").assertTextContains("Error las contraseñas no coinciden")

    }

    @Test
    fun Medical_CheckIn_Null_Error(){
        val name=""
        val dni=""
        val tuit=""
        val correo=""
        val pass=""
        val confpass=""
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SessionNavHost(navController,"Beginning/?newText=","")
        }
        composeTestRule.onNodeWithText("Registrarse").performClick()
        composeTestRule.onNodeWithText("MEDICO").performClick()
        medical_Form_test(name,dni,correo,pass,confpass,tuit)

    }

    fun medical_Form_test(name:String, dni:String, correo:String, pass:String, confpass:String,tuit:String){

        composeTestRule.onNodeWithTag("Name_medical").performTextInput(name)
        composeTestRule.onNodeWithTag("Name_medical").assertTextContains(name)
        composeTestRule.onNodeWithTag("Dni_medical").performTextInput(dni)
        composeTestRule.onNodeWithTag("Dni_medical").assertTextContains(dni)
        composeTestRule.onNodeWithTag("Tuition_medical").performTextInput(tuit)
        composeTestRule.onNodeWithTag("Tuition_medical").assertTextContains(tuit)
        composeTestRule.onNodeWithTag("Correo_medical").performTextInput(correo)
        composeTestRule.onNodeWithTag("Correo_medical").assertTextContains(correo)
        composeTestRule.onNodeWithTag("Pass_medical").performTextInput(pass)
        composeTestRule.onNodeWithContentDescription("Ocultar contraseña medicals").performClick()
        composeTestRule.onNodeWithTag("Pass_medical").assertTextContains(pass)
        composeTestRule.onNodeWithTag("ConfPass_medical").performTextInput(confpass)
        composeTestRule.onNodeWithTag("ConfPass_medical").assertTextContains(confpass)
        composeTestRule.onNodeWithText("Confirmar medicals").performClick()

    }



}