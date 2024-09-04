package com.prac.sampleappcompose.composables.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage


/**
 * this composable used to render image from url
 * using coil library to load image
 */
@Composable
fun LoadImage(url: String?, modifier: Modifier){
    AsyncImage(
        model = url, contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop // crop the image to fit with the bounds
    )
}
