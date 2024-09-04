package com.prac.sampleappcompose.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.prac.sampleappcompose.models.Meme
import com.prac.sampleappcompose.util.MemeUtil


/**
 * this composable used to render search and filter list
 */
@ExperimentalMaterial3Api
@Composable
fun SearchAndFilterList(items: List<Meme>, showSheet: MutableState<Boolean>) {

    // Filter list based on search query
    val searchQuery = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        // Search TextField
        TextField(
            value = searchQuery.value,
            onValueChange = { newText ->
                searchQuery.value = newText
            },
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = "Search Icon")
            },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(1.dp, Color.Gray),
                    RoundedCornerShape(16.dp)
                ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent, // Make the background transparent
                focusedIndicatorColor = Color.Transparent, // Remove the focused indicator line
                unfocusedIndicatorColor = Color.Transparent // Remove the unfocused indicator line
            ),
            placeholder = { Text(text = "Search") }
        )

        // Filtering the list based on the search query
        val filteredItems by rememberUpdatedState(
            items.filter {
                it.title?.contains(searchQuery.value, ignoreCase = true) == true
            }
        )

        // Show the bottom sheet if the search query is not empty
        if (showSheet.value) {
            BottomSheet(filteredItems.size, MemeUtil.statisticsBottomSheet(filteredItems)) {
                showSheet.value = false
            }
        }

        // Spacer to add some space between the search and filter list
        Spacer(modifier = Modifier.height(16.dp))

        // Show the filtered list or a message if no items match
        if (filteredItems.isNotEmpty()) {
            ShowMemeList(items = filteredItems)
        } else {
            // Show an empty state message
            Text(
                text = "No results found",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
