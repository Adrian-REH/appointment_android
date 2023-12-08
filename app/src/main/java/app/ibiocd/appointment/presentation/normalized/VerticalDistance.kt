package app.ibiocd.appointment.presentation.normalized

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun BlockSpace(size: Int = 20) = Spacer(modifier = Modifier.size(size.dp))
@Composable
fun BlockSpaces(size: Int = 50) = Spacer(modifier = Modifier.size(size.dp))
@Composable
fun Spacers(size: Int = 10) = Spacer(modifier = Modifier.size(size.dp))

@Composable
fun SimpleSpacers(size: Int = 5) = Spacer(modifier = Modifier.size(size.dp))