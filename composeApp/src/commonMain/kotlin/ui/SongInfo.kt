package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import model.OpenLyricsSong

@Composable
fun SongInfo(
    lyricsSong: OpenLyricsSong,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier.padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(text = "Song info", style = MaterialTheme.typography.h4)
        Spacer(Modifier.height(16.dp))

        Text(text = lyricsSong.properties.titles.first().value, style = MaterialTheme.typography.h5)
        Text(
            text = "Songbook: ${lyricsSong.properties.songbooks?.first()?.name}, No. ${lyricsSong.properties.songbooks?.first()?.entry}",
            style = MaterialTheme.typography.h6
        )

        Spacer(Modifier.height(16.dp))

        lyricsSong.lyrics.forEach { verse ->
            Text(verse.name, fontWeight = FontWeight.Bold)
            Text(text = verse.lines.joinToString(separator = "\n") { it.content })
            Spacer(Modifier.height(16.dp))
        }
    }
}
