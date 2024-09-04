package com.prac.sampleappcompose.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.prac.sampleappcompose.composables.util.LoadImage


/**
 * this composable used to render image carousel
 * take list of image urls as input
 */
@ExperimentalPagerApi
@Composable
fun Carousel(urls: List<String?>) {
    Box {
        val pagerState = rememberPagerState()

        // declare modifier for image
        // to re-use image composable
        val modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(22.dp)
            .border(
                BorderStroke(1.dp, Color.Gray),
                RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clipToBounds()

        // use dot indicators to highlight current image
        DotIndicators(
            pageCount = urls.size,
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        // implementing image carousel through horizontal pager
        HorizontalPager(
            contentPadding = PaddingValues(horizontal = 0.dp),
            itemSpacing = 12.dp,
            count = urls.size,
            state = pagerState
        ) {
            // load image from url
            LoadImage(urls[it], modifier)
        }
    }
}


/**
 * this composable used to render dot indicators of image carousel
 * highlight current image indicator
 */
@ExperimentalPagerApi
@Composable
fun DotIndicators(
    pageCount: Int,
    pagerState: PagerState,
    modifier: Modifier
) {
    Row(modifier = modifier) {
        repeat(pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Color.Blue else Color.Gray
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            if (iteration < pageCount - 1) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}


