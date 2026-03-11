package com.example.wearos.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.wear.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import androidx.wear.compose.ui.tooling.preview.WearPreviewFontScales
import com.example.wearos.presentation.theme.WearOsTheme
import java.time.LocalTime
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    WearOsTheme {
        AppScaffold {
            ScreenScaffold {
                ElegantWatchFace(brandName = "CROWN ÉLITE")
            }
        }
    }
}

@Composable
private fun ElegantWatchFace(brandName: String) {
    var currentTime by remember { mutableStateOf(LocalTime.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalTime.now()
            delay(1000)
        }
    }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Brush.radialGradient(colors = listOf(Color(0xFF2B1D0E), Color(0xFF0B0B0B))))
                .padding(10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier =
                Modifier
                    .size(172.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(Color(0xCC101010)),
            contentAlignment = Alignment.Center,
        ) {
            AnalogDial(currentTime)

            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(vertical = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = brandName,
                    color = Color(0xFFD4AF37),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp,
                )
                TimeOverlay(currentTime)
            }
        }
    }
}

@Composable
private fun AnalogDial(time: LocalTime) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = min(size.width, size.height) / 2

        drawCircle(color = Color(0xFF1A1A1A), radius = radius * 0.95f)
        drawCircle(color = Color(0xFFD4AF37), radius = radius * 0.95f, style = Stroke(3f))

        repeat(12) { index ->
            val angle = Math.toRadians((index * 30.0) - 90.0)
            val start =
                Offset(
                    x = center.x + cos(angle).toFloat() * radius * 0.78f,
                    y = center.y + sin(angle).toFloat() * radius * 0.78f,
                )
            val end =
                Offset(
                    x = center.x + cos(angle).toFloat() * radius * 0.88f,
                    y = center.y + sin(angle).toFloat() * radius * 0.88f,
                )
            drawLine(color = Color(0xFFB88A2C), start = start, end = end, strokeWidth = 3f)
        }

        val secondAngle = Math.toRadians((time.second * 6.0) - 90.0)
        val minuteAngle = Math.toRadians((time.minute * 6.0 + time.second * 0.1) - 90.0)
        val hourAngle = Math.toRadians(((time.hour % 12) * 30.0 + time.minute * 0.5) - 90.0)

        drawLine(
            color = Color(0xFFC5A35B),
            start = center,
            end =
                Offset(
                    x = center.x + cos(hourAngle).toFloat() * radius * 0.42f,
                    y = center.y + sin(hourAngle).toFloat() * radius * 0.42f,
                ),
            strokeWidth = 7f,
            cap = StrokeCap.Round,
        )

        drawLine(
            color = Color(0xFFF0D8A0),
            start = center,
            end =
                Offset(
                    x = center.x + cos(minuteAngle).toFloat() * radius * 0.60f,
                    y = center.y + sin(minuteAngle).toFloat() * radius * 0.60f,
                ),
            strokeWidth = 5f,
            cap = StrokeCap.Round,
        )

        drawLine(
            color = Color(0xFFE54444),
            start = center,
            end =
                Offset(
                    x = center.x + cos(secondAngle).toFloat() * radius * 0.68f,
                    y = center.y + sin(secondAngle).toFloat() * radius * 0.68f,
                ),
            strokeWidth = 2.5f,
            cap = StrokeCap.Round,
        )

        drawCircle(color = Color(0xFFD4AF37), radius = 6f, center = center)
    }
}

@Composable
private fun TimeOverlay(time: LocalTime) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "%02d:%02d".format(time.hour, time.minute),
            color = Color(0xFFF6E8C3),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "LUXURY SERIES",
            color = Color(0xFFB88A2C),
            fontSize = 10.sp,
            letterSpacing = 1.2.sp,
        )
    }
}

@WearPreviewDevices
@WearPreviewFontScales
@Composable
fun DefaultPreview() {
    WearApp()
}
