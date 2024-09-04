package com.prac.sampleappcompose.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.prac.sampleappcompose.viewmodel.MemeViewModel


/**
 * This app composable used to render
 * image carousel,
 * search and filter list,
 * bottom sheet,
 * and floating action button
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun App() {
    val viewModel: MemeViewModel = viewModel()
    val imageList by viewModel.images.collectAsState()
    val itemList by viewModel.memeList.collectAsState()
    val showSheet = remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            // floating action button
            FloatingActionButton(
                onClick = {
                    // open bottom sheet
                    // change showSheet state to true
                    showSheet.value = true
                },
                containerColor = Color.Blue,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.MoreVert, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->
            Column(Modifier.padding(paddingValues)) {

                // image carousel composable
                Carousel(imageList)

                // search and filter list composable
                SearchAndFilterList(items = itemList, showSheet)
            }
        }
    )
}
