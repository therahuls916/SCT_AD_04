// FILE: ui/components/ActionCard.kt

package com.rahul.auric.auricscan.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rahul.auric.auricscan.R
import com.rahul.auric.auricscan.ui.theme.AuricScanTheme

@Composable
fun ActionCard(
    title: String,
    subtitle: String,
    buttonText: String,
    imageResId: Int,
    onButtonClick: () -> Unit,
    iconTintColor: Color? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    val colorFilter = if (iconTintColor != null) ColorFilter.tint(iconTintColor) else null
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = title,
                        modifier = Modifier.size(100.dp),
                        colorFilter = colorFilter
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = subtitle,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
                Button(
                    onClick = onButtonClick,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(buttonText)
                }
            }
        }
    }
}

@Preview
@Composable
fun ActionCardPreview() {
    AuricScanTheme {
        ActionCard(
            title = "Scan QR Code",
            subtitle = "Use your camera to scan",
            buttonText = "Scan",
            imageResId = R.drawable.ic_camera, // Assuming ic_camera is available
            onButtonClick = {},
            iconTintColor = MaterialTheme.colorScheme.primary
        )
    }
}