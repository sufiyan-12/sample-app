package com.prac.sampleappcompose.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prac.sampleappcompose.composables.util.LoadImage
import com.prac.sampleappcompose.models.Meme


/**
 * this composable used to render list of meme
 */
@Composable
fun ShowMemeList(items: List<Meme>) {
    // using lazy column to render list
    LazyColumn {
        items(items) { item ->
            ListItem(item = item)
        }
    }
}

/**
 * this composable used to render list item
 */
@Composable
fun ListItem(item: Meme) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .border(
                BorderStroke(1.dp, Color.LightGray), // Border thickness and color
                RoundedCornerShape(8.dp)          // Rounded corners
            )
            .padding(6.dp)
            .fillMaxWidth(1f)
    ) {

        Column(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color.Blue)
        ) {
            LoadImage(url = item.image, Modifier.fillMaxWidth())
        }

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = item.title!!,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                color = Color.Black
            )

            Text(
                text = item.subTitle!!,
                fontSize = 14.sp,
                textAlign = TextAlign.Start,
                color = Color.Gray
            )
        }
    }
}