import kotlinx.coroutines.*
import java.io.File
import java.io.IOException
import javax.sound.sampled.*

class VoiceRecorder {

    private val format = AudioFormat(
        AudioFormat.Encoding.PCM_SIGNED,
        16000.0F,
        16,
        1,
        2,
        16000.0F,
        false
    )


    private val info = DataLine.Info(TargetDataLine::class.java, format)

    private val line = AudioSystem.getLine(info) as TargetDataLine

//    private val cr = CoroutineScope(Dispatchers.Main)
    private val cr = CoroutineScope(Dispatchers.Main)



    init {
        if (!AudioSystem.isLineSupported(info)) {
            println("Line is not supported!!!")
        }else{
            println("Line is supported.")
        }
    }


    fun startRecording(){


        line.open()
        line.start()

        val recordingStream = AudioInputStream(line)
        val outputFile = File("src/main/resources/record.wav")

        val frameRate = format.frameRate

        runBlocking {

            while(true){
                val piu = line.framePosition/frameRate
                delay(100)
                println("OK $piu")
                println(line.framePosition)
                println(line.longFramePosition)
                println(line.level)
            }

//            while(line.framePosition/frameRate<1.0){
//                val piu = line.framePosition/frameRate
//                delay(100)
//                println("OK $piu")
//                println(line.isOpen)
//            }




        }




//        AudioSystem.write(recordingStream, AudioFileFormat.Type.WAVE, outputFile)
//        line.stop()
//        line.close()

    }



    @Throws(UnsupportedAudioFileException::class, IOException::class)
    fun getPcmByteArray(filename: String?): javax.sound.sampled.AudioFormat {
        val inputFile = File(filename)
        val audioInputStream = AudioSystem.getAudioInputStream(inputFile)
        return audioInputStream.format

    }

}