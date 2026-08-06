package la.devpicon.android.mydrawingsapplication.composable.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import la.devpicon.android.mydrawingsapplication.R
import la.devpicon.android.mydrawingsapplication.draw.ScratchCard
import la.devpicon.android.mydrawingsapplication.ui.theme.MyDrawingsApplicationTheme

internal const val SCRATCH_CARD_RESET_TEST_TAG = "scratchCardReset"

@Composable
fun ScratchCardScreen(modifier: Modifier = Modifier) {
    var scratchCardInstance by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.scratch_card_instruction),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        key(scratchCardInstance) {
            ScratchCard(
                modifier = Modifier
                    .widthIn(max = 360.dp)
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp)),
                strokeWidth = 52.dp
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color(0xFFFFD700)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.scratch_card_prize),
                        modifier = Modifier.padding(24.dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { scratchCardInstance++ },
            modifier = Modifier.testTag(SCRATCH_CARD_RESET_TEST_TAG)
        ) {
            Text(text = stringResource(R.string.scratch_card_reset))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScratchCardScreenPreview() {
    MyDrawingsApplicationTheme {
        Surface {
            ScratchCardScreen()
        }
    }
}
