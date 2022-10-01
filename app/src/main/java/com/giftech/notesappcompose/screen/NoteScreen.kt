package com.giftech.notesappcompose.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giftech.notesappcompose.R
import com.giftech.notesappcompose.components.NoteButton
import com.giftech.notesappcompose.components.NoteInputText
import com.giftech.notesappcompose.data.NoteDataSource
import com.giftech.notesappcompose.model.Note
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalComposeUiApi
@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    Column(Modifier.padding(6.dp)) {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.app_name))
            },
            actions = {
                Icon(imageVector = Icons.Rounded.Notifications, contentDescription = "icon")
            }
        )
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoteInputText(
                modifier = Modifier.padding(vertical = 8.dp),
                text = title,
                label = "Title",
                onTextChange = {
                    if (it.all { char -> char.isLetter() || char.isWhitespace() }) {
                        title = it
                    }
                })
            NoteInputText(
                modifier = Modifier.padding(vertical = 8.dp),
                text = description,
                label = "Add a note",
                onTextChange = {
                    if (it.all { char -> char.isLetter() || char.isWhitespace() }) {
                        title = it
                    }
                })
            NoteButton(
                text = "Save",
                onClick = {
                    if (title.isNotEmpty() && description.isNotEmpty()) {
                        //save to list
                        title = ""
                        description = ""
                    }
                }
            )
        }
        Divider(Modifier.padding(8.dp))
        LazyColumn {
            items(notes) { note ->
                NoteRow(note = note, onNoteClick = {})
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClick: (Note) -> Unit
) {
    Surface(
        modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(topEnd = 16.dp, bottomStart = 16.dp))
            .fillMaxWidth(),
        color = MaterialTheme.colors.secondaryVariant,
        elevation = 6.dp
    ) {
        Column(
            modifier
                .clickable { }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                note.title,
                color = MaterialTheme.colors.onSecondary,
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                note.description,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                note.entryDate.format(DateTimeFormatter.ofPattern("EEE, d MMM yy h a")),
                        style = MaterialTheme.typography.caption
            )

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    NoteScreen(notes = NoteDataSource().loadNotes(), onAddNote = {}, onRemoveNote = {})
}