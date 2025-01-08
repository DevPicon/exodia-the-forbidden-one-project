package la.devpicon.android.mydrawingsapplication.composable.screen

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import la.devpicon.android.mydrawingsapplication.ui.theme.MyDrawingsApplicationTheme
import java.io.File
import java.io.FileOutputStream

@Composable
fun PixelArtScreen(modifier: Modifier = Modifier) {
    val gridWidth = 27
    val gridHeight = 54
    val squareSize = 20.dp
    val colors = listOf(
        Color.Blue, Color.Cyan, Color.Yellow,
        Color.Green, Color(0xfffe9441),
        Color.Red, Color.Black, Color.White, Color(0xff5c3613)
    )
    var selectedColor by remember { mutableStateOf(Color.Black) }
    var tapGestureOffset by remember { mutableStateOf(Offset.Zero) }
    var calculatedCellSize by remember { mutableFloatStateOf(squareSize.value) }

    val gridColors = remember {
        mutableStateListOf<Color>().apply {
            repeat(gridWidth * gridHeight) {
                add(Color.White)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Color picker
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            colors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(color)
                        .clickable { selectedColor = color }
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    Text("x:${tapGestureOffset.x}")
                    Text("y:${tapGestureOffset.y}")
                }

                Row {
                    Text("column:${(tapGestureOffset.x / calculatedCellSize).toInt()}")
                    Text("row:${(tapGestureOffset.y / calculatedCellSize).toInt()}")
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                val context = LocalContext.current.applicationContext

                // Export Button
                Button(
                    onClick = {  exportPixelArt(context, gridColors, gridWidth, gridHeight) },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Export")
                }
            }
        }


        // Pixel Art Grid
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        tapGestureOffset = offset
                        val x = (offset.x / calculatedCellSize).toInt()
                        val y = (offset.y / calculatedCellSize).toInt()
                        val index = y * gridWidth + x
                        if (index in gridColors.indices) {
                            gridColors[index] = selectedColor
                        }
                    }
                }
        ) {
            val cellSize = size.width / gridWidth
            calculatedCellSize = cellSize
            for (y in 0 until gridHeight) {
                for (x in 0 until gridWidth) {
                    val color = gridColors[y * gridWidth + x]
                    drawRect(
                        color = color,
                        topLeft = Offset(x * cellSize, y * cellSize),
                        size = androidx.compose.ui.geometry.Size(cellSize, cellSize),
                        style = Fill
                    )
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(x * cellSize, y * cellSize),
                        size = androidx.compose.ui.geometry.Size(cellSize, cellSize),
                        style = Stroke(width = 1f)
                    )
                }
            }
        }

    }
}

// Export logic
fun exportPixelArt(
    context: Context,
    gridColors: List<Color>,
    width: Int,
    height: Int
) {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)

    val cellSize = bitmap.width / width.toFloat()

    // Draw the grid colors on the canvas
    for (y in 0 until height) {
        for (x in 0 until width) {
            val color = gridColors[y * width + x].toArgb()
            val paint = Paint().apply { this.color = color }
            canvas.drawRect(
                Rect(
                    (x * cellSize).toInt(),
                    (y * cellSize).toInt(),
                    ((x + 1) * cellSize).toInt(),
                    ((y + 1) * cellSize).toInt()
                ),
                paint
            )
        }
    }

    // Save the bitmap using Scoped Storage
    val fileName = "pixel_art.png"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // Use MediaStore API for Scoped Storage
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(
                MediaStore.Images.Media.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + "/PixelArt"
            )
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        if (uri != null) {
            resolver.openOutputStream(uri).use { outputStream ->
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                }
            }
        }
    } else {
        // Legacy approach for devices below Android 10
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val file = File(directory, fileName)

        FileOutputStream(file).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
    }
}


@Preview
@Composable
private fun PixelArtScreenPreview() {
    MyDrawingsApplicationTheme {
        Surface {
            PixelArtScreen()
        }
    }
}