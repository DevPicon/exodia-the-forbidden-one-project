package la.devpicon.android.mydrawingsapplication.composable.screen

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Data class representing a single firework spark
 * @property position Current position of the spark
 * @property velocity Speed and direction of the spark
 * @property color Color of the spark
 * @property alpha Current opacity of the spark
 */
data class Spark(
    var position: Offset,
    val velocity: Offset,
    val color: Color,
    var alpha: Float
)

/**
 * Main composable that displays the New Year celebration screen with animated fireworks
 */
@Composable
fun FireworksScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // Display year text
        Text(
            text = "2025",
            color = Color.White,
            fontSize = 72.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )

        // Display animated fireworks
        FireworksAnimation()
    }
}

/**
 * Composable that handles the fireworks animation
 * Each firework explosion creates multiple sparks that follow physics-based motion
 */
@Composable
fun FireworksAnimation() {
    var sparks by remember { mutableStateOf(listOf<Spark>()) }
    val infiniteTransition = rememberInfiniteTransition(label = "")

    // Animation ticker
    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    // Create new fireworks periodically
    LaunchedEffect(animationProgress) {
        val newSparks = createFireworkExplosion(
            Offset(
                Random.nextFloat() * 1000f,
                Random.nextFloat() * 1500f
            )
        )
        sparks = (sparks + newSparks).filter { it.alpha > 0 }

    }

    // Draw all sparks
    Canvas(modifier = Modifier.fillMaxSize()) {
        sparks.forEach { spark ->
            drawCircle(
                color = spark.color,
                radius = 4f,
                center = spark.position,
                alpha = spark.alpha
            )

            // Update spark position and properties
            spark.position += spark.velocity
            spark.alpha -= 0.01f
        }
    }
}

/**
 * Creates a new firework explosion at the specified position
 * @param position Starting position of the firework explosion
 * @return List of sparks that make up the explosion
 */
private fun createFireworkExplosion(position: Offset): List<Spark> {
    val sparkCount = 50
    val sparks = mutableListOf<Spark>()

    // Create sparks in a circular pattern
    repeat(sparkCount) { i ->
        val angle = (i.toFloat() / sparkCount) * 2 * Math.PI.toFloat()
        val speed = Random.nextFloat() * 10f + 5f
        val velocity = Offset(
            cos(angle) * speed,
            sin(angle) * speed
        )

        sparks.add(
            Spark(
                position = position,
                velocity = velocity,
                color = Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat()
                ),
                alpha = 1f
            )
        )
    }

    return sparks
}

@Preview(showBackground = true)
@Composable
fun FireworksScreenPreview() {
    FireworksScreen()
}