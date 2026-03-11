package com.example.wearos.presentation.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material3.MaterialTheme

@Composable
fun CRPrueba(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}

/**
 * Backward-compatible alias for previous callers.
 */
@Composable
fun WearOsTheme(content: @Composable () -> Unit) {
    CRPrueba(content = content)
}
