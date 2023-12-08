package app.ibiocd.appointment.presentation.screens.Appointment

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun OdontogramWeb(){

    // Declare a string that contains a url
    val mUrl = "https://drones-app-63b2f.web.app"

    // Adding a WebView inside AndroidView
    // with layout as full screen
    AndroidView(factory = {

        WebView(it)
            .apply {
                settings.javaScriptEnabled=true
                settings.javaScriptCanOpenWindowsAutomatically =true

                layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(mUrl)

        }
    }, update = {
        it.loadUrl(mUrl)
    })
}
