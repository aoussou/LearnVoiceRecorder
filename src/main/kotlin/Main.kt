// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine
import javax.sound.sampled.TargetDataLine
import javax.sound.sampled.UnsupportedAudioFileException


@Composable
@Preview
fun App() {


    val vr = VoiceRecorder()


    MaterialTheme {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(onClick = {

                println("before click")

                vr.recordForSpecificDuration(duration = 10.0)

                println("after click")

            }){ Text("record") }
        }
    }



    @Throws(UnsupportedAudioFileException::class, IOException::class)
    fun getPcmByteArray(filename: String?): javax.sound.sampled.AudioFormat {
        val inputFile = File(filename)
        val audioInputStream = AudioSystem.getAudioInputStream(inputFile)
        return audioInputStream.format

    }

}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
