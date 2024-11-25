package la.devpicon.android.mydrawingsapplication.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import la.devpicon.android.mydrawingsapplication.ui.theme.MyDrawingsApplicationTheme

@Composable
fun Menu(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val buttonModifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp)

        Button(
            onClick = {},
            modifier = buttonModifier
        ) {
            Text("Ejemplo básico")
        }

        Button(
            onClick = {},
            modifier = buttonModifier
        ) {
            Text("Gráfico de posesión en Android")
        }

        Button(
            onClick = {},
            modifier = buttonModifier
        ) {
            Text("Temporizador interactivo")
        }

        Button(
            onClick = {},
            modifier = buttonModifier
        ) {
            Text("Linea de comparación")
        }
    }
}

@Preview
@Composable
private fun MenuPreview() {
    MyDrawingsApplicationTheme {
        Menu()
    }
}