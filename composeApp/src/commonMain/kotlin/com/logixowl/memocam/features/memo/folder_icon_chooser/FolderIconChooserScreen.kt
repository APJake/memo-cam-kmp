package com.logixowl.memocam.features.memo.folder_icon_chooser

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.logixowl.memocam.model.FolderIcon
import com.logixowl.memocam.ui.extensions.asChoosableList
import com.logixowl.memocam.ui.extensions.asImageVector
import com.logixowl.memocam.ui.themes.AppTheme
import com.logixowl.memocam.ui.themes.spacing
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Created by AP-Jake
 * on 27/06/2025
 */

@Composable
fun FolderIconChooserScreen(
    selectedIcon: FolderIcon? = null,
    onSelectIcon: (FolderIcon) -> Unit,
    onClickedBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val iconList = remember { FolderIcon.asChoosableList }
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium2)
    ) {
        items(iconList) { icon ->
            IconButton(onClick = { onSelectIcon(icon) }) {
                Icon(imageVector = icon.asImageVector, contentDescription = null)
            }
        }
    }
}

@Preview
@Composable
private fun FolderIconChooserScreenPreview(){
    AppTheme {
        FolderIconChooserScreen(
            selectedIcon = null,
            onSelectIcon = {},
            onClickedBack = {},
        )
    }
}
