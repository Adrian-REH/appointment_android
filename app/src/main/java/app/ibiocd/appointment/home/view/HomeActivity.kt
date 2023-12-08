package app.ibiocd.appointment.home.view

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import app.ibiocd.appointment.home.navegation.NavigationHome
import app.ibiocd.appointment.ui.theme.AppointmentTheme
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity(), OnMapReadyCallback {

    private lateinit var map:GoogleMap


    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            } else -> {
            // No location access granted.
        }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map=googleMap

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))


        setContent {
            val navControllerHome = rememberNavController()
            val navController = rememberNavController()

            val context= LocalContext.current
            var Medical:Boolean=false
            var ID:String=""

            if(intent.extras !=null){
                Medical = intent.getStringExtra("medicals").toString().toBoolean()
                ID = intent.getStringExtra("id").toString()
                Log.d("HOME->ID",ID)

            }
            AppointmentTheme {
                Surface(color = MaterialTheme.colors.background) {

                    NavigationHome(Medical = Medical,user = ID,navControllerHome,navController)

                }
            }
        }
    }






}
