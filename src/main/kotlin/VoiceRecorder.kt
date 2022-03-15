import kotlinx.coroutines.*
import java.io.File
import java.io.IOException
import javax.sound.sampled.*

class VoiceRecorder(
    private val format: AudioFormat = AudioFormat(
        AudioFormat.Encoding.PCM_SIGNED,
        16000.0F,
        16,
        1,
        2,
        16000.0F,
        false
    )
) {



    private val info = DataLine.Info(TargetDataLine::class.java, format)
    private val line = AudioSystem.getLine(info) as TargetDataLine
    private val cr = CoroutineScope(Dispatchers.IO)

    init {
        if (!AudioSystem.isLineSupported(info)) {
            println("Line is not supported!!!")
        }else{
            println("Line is supported.")
        }
    }

    fun recordForSpecificDuration(duration:Double){

        line.open()
        line.start()

        val recordingStream = AudioInputStream(line)
        val outputFile = File("src/main/resources/record.wav")
        val frameRate = format.frameRate


        cr.launch { AudioSystem.write(recordingStream, AudioFileFormat.Type.WAVE, outputFile) }

        runBlocking {
            while(line.framePosition/frameRate < duration){
                val time = line.framePosition/frameRate
                delay(100)
                println("position: ${line.framePosition}, time: $time")
            }
        }

        line.stop()
        line.close()
    }
}
