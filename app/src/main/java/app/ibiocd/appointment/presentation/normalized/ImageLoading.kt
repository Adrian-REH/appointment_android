package app.ibiocd.appointment.presentation.normalized

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer


@Composable
fun ImageLoading() {

    Box(
        modifier = Modifier
            .size(40.dp)
            .shimmer()
            .background(Color.Gray)
            .clip(CircleShape)
    )

}