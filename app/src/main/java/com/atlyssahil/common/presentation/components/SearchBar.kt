package com.atlyssahil.common.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 20.dp),
        value = "Sahiljeet",
        onValueChange = {
            //onEvent(SearchUiEvents.SearchTermChanged(it))
        },
        placeholder = {
            Text(
                text = "Search movies",
                color = MaterialTheme.colorScheme.onBackground.copy(.5f),
            )
        },
        shape = MaterialTheme.shapes.large,
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Text,
        ),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = MaterialTheme.colorScheme.background,
        ),
        maxLines = 1,
        singleLine = true,
        trailingIcon = {
            /*if (state.searchTerm.isNotEmpty()) {
                IconButton(onClick = {
                    onEvent(SearchUiEvents.ClearSearchTerm)
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MaterialTheme.colorScheme.onBackground.copy(.5f),
                        contentDescription = null
                    )
                }
            }*/
        },
    )
}

@Composable
@Preview
fun SearchPreview() {
    SearchBar()
}