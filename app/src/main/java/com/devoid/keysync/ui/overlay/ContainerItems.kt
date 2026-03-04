package com.devoid.keysync.ui.overlay

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devoid.keysync.R

// ... (CircularTextField, RemoveButton, IconKey remain the same)

@Composable
fun CircularTextField(
    value: String,
    borderColor: Color = MaterialTheme.colorScheme.secondary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape)
            .border(width = 1.dp, color = borderColor, CircleShape)
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))

    ) {
        Text(
            value,
            modifier = Modifier
                .wrapContentSize()
                .padding(4.dp)
                .align(Alignment.Center), style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.inverseSurface,
            )
        )
    }
}

@Composable
fun RemoveButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(20.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Remove",
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun IconKey(
    modifier: Modifier = Modifier.alpha(0.0f),
    background:Color=MaterialTheme.colorScheme.surface,
    borderColor: Color,
    onClick: (() -> Unit)?,
    removable: Boolean = true,
    onRemove: () -> Unit,
    icon: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .size(50.dp), contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .border(1.dp, borderColor, CircleShape)
                .background(background.copy(alpha = 0.5f))
                .clickable (enabled = onClick!=null, onClick = onClick?:{}),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }
        if (removable) {
            RemoveButton(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(24.dp, -24.dp), onClick = onRemove
            )
        }
    }
}

// --- NEW MACRO KEY COMPOSABLE ---
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MacroKey(
    modifier: Modifier = Modifier,
    value: String,
    delay: Long,
    borderColor: Color = Color.Magenta, // Distinct color for Macros
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onRemove: () -> Unit,
) {
    Box(modifier = modifier.padding(8.dp)) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(width = 1.dp, color = borderColor, CircleShape)
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                )
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.inverseSurface
                    )
                )
                Text(
                    text = "${delay}ms",
                    style = TextStyle(
                        fontSize = 8.sp,
                        color = borderColor.copy(alpha = 0.8f)
                    )
                )
            }
        }
        RemoveButton(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(24.dp, -24.dp),
            onClick = onRemove
        )
    }
}

// ... (Rest of your existing keys: BagMapKey, CancelKey, MousePointer, FireKey, WASD)

@Composable
fun BagMapKey(
    modifier: Modifier = Modifier.padding(8.dp),
    borderColor: Color = MaterialTheme.colorScheme.primary,
    text: String = "ads",
    onClick: (() -> Unit)?,
    onRemove: () -> Unit
) {
    IconKey(modifier = modifier, borderColor = borderColor, onClick = onClick, onRemove = onRemove) {
        Image(
            modifier = Modifier
                .alpha(0.6f)
                .fillMaxSize()
                .padding(2.dp),
            painter = painterResource(R.drawable.bag),
            contentDescription = "pointer",
            colorFilter = ColorFilter.tint(borderColor)
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.inverseSurface
        )
    }
}

@Composable
fun CancelKey(
    modifier: Modifier = Modifier.padding(8.dp),
    borderColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    IconKey(modifier = modifier, removable = false, onRemove = {}, onClick = onClick, borderColor = borderColor) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            painter = painterResource(R.drawable.cancel),
            contentDescription = "pointer",
            colorFilter = ColorFilter.tint(borderColor)
        )
    }
}

@Composable
fun MousePointer(
    modifier: Modifier = Modifier.padding(8.dp),
    borderColor: Color = MaterialTheme.colorScheme.primary,
    onRemove: () -> Unit
) {
    IconKey(modifier=modifier,borderColor = borderColor, onClick = null, onRemove = onRemove, icon = {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            painter = painterResource(R.drawable.move),
            contentDescription = "pointer",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
        )
    })
}

@Composable
fun FireKey(
    modifier: Modifier = Modifier.padding(8.dp),
    borderColor: Color = MaterialTheme.colorScheme.primary,
    onRemove: () -> Unit
) {

    IconKey(modifier=modifier, borderColor = borderColor, onClick = null, onRemove = onRemove) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            painter = painterResource(R.drawable.bullet),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            contentDescription = "fire"
        )
    }

}

@Composable
fun WASDKeysGroup(
    modifier: Modifier = Modifier,
    onRemove: () -> Unit,
    onPanIconDrag: (Offset) -> Unit
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), CircleShape)
                .background(Color.Black.copy(alpha = 0.1f))
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                CircularTextField("W", onClick = {})
                Spacer(modifier = Modifier.size(22.dp))
                CircularTextField("S", onClick = {})
            }
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                CircularTextField("A", onClick = {})
                Spacer(modifier = Modifier.size(22.dp))
                CircularTextField("D", onClick = {})
            }

        }
        RemoveButton(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(56.dp, (-56).dp), onClick = onRemove
        )
        IconButton(
            onClick = {}, modifier = Modifier
                .align(Alignment.Center)
                .offset(56.dp, 56.dp)
                .size(16.dp)
                .rotate(-90f)
                .pointerInput("wasd") {
                    detectDragGestures { change, dragAmount ->
                        onPanIconDrag(dragAmount)
                    }
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.pan_zoom),
                contentDescription = "Scale",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun KeyCircular(
    modifier: Modifier = Modifier,
    value: String,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
    onRemove: () -> Unit,
) {
    Box(modifier = modifier.padding(8.dp)) {
        CircularTextField(value = value, borderColor, onClick = onClick)
        RemoveButton(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(24.dp, -24.dp), onClick = onRemove
        )
    }
}
