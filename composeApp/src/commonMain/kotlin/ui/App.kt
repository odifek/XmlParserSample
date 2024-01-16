package ui

import Greeting
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import model.OpenLyricsSong
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import usecase.ReadOpenLyricsUseCase

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        val greeting = remember { Greeting().greet() }
        val coroutineScope = rememberCoroutineScope()
        var openLyricsSong: OpenLyricsSong? by remember { mutableStateOf(null) }
        var hasError by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                if (openLyricsSong == null) {
                    coroutineScope.launch {
                        val useCase = ReadOpenLyricsUseCase()
                        val result = useCase("assets/amazing_grace.xml")
                        if (result.isSuccess) {
                            openLyricsSong = result.getOrThrow()
                        } else {
                            hasError = true
                        }
                    }
                    showContent = !showContent
                } else {
                    showContent = !showContent
                }
            }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                if (hasError || openLyricsSong == null) {
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Compose: $greeting")
                        Text("Unable to read lyrics xml", color = MaterialTheme.colors.error)
                    }
                } else {
                    SongInfo(openLyricsSong!!, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}