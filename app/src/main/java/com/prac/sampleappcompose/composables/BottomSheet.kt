package com.prac.sampleappcompose.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * this bottom sheet composable used to render count of occurrences of top 3 characters
 */
@ExperimentalMaterial3Api
@Composable
fun BottomSheet(itemCount: Int, charCounts: Map<Char, Int>, onDismiss: () -> Unit) {

    val modalBottomSheetState = rememberModalBottomSheetState()

    // render bottom sheet
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Item count: $itemCount",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text("Top 3 characters:")
            charCounts.forEach { (char, count) ->
                Text("Character: $char = Count: = $count")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
