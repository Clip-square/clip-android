package com.qpeterp.clip.domain.usecase.meeting

import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import com.qpeterp.clip.common.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.coroutines.cancellation.CancellationException

class AudioRecodeUseCase {

    private var audioRecord: AudioRecord? = null
    private var isRecording = false
    private var isPaused = false
    private var byteArrayOutputStream: ByteArrayOutputStream? = null
    private var bufferSize: Int = 0
    private var buffer: ByteArray? = null

    suspend fun startRecording(context: Context): File {
        val sampleRate = 44100 // 샘플링 주파수
        val audioFormat = AudioFormat.ENCODING_PCM_16BIT
        val channelConfig = AudioFormat.CHANNEL_IN_MONO
        bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

        // AudioRecord 인스턴스 생성
        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channelConfig,
            audioFormat,
            bufferSize
        )

        // 버퍼 준비
        buffer = ByteArray(bufferSize)
        byteArrayOutputStream = ByteArrayOutputStream()

        // 녹음 시작
        audioRecord?.startRecording()
        isRecording = true

        Log.d(Constant.TAG, "AudioRecodeUseCase startRecording is running")

        try {
            withContext(Dispatchers.IO) {
                Log.d(Constant.TAG, "isRecording while START")
                while (isRecording) {
                    if (!isPaused) {
                        val read = audioRecord?.read(buffer!!, 0, buffer!!.size) ?: 0
                        if (read > 0) {
                            byteArrayOutputStream?.write(buffer, 0, read)
                        }
                    } else {
                        // 일시정지 상태에서는 100ms 대기하며 CPU 과부하 방지
                        kotlinx.coroutines.delay(100)
                    }
                }
                Log.d(Constant.TAG, "isRecording while END")
            }

        } catch (e: CancellationException) {
            // 코루틴 취소 시 처리
            Log.d(Constant.TAG, "코루틴이 취소되었습니다: ${e.message}")
        } finally {
            audioRecord?.stop()
            audioRecord?.release()
            Log.d(Constant.TAG, "끝이 났습니다. GG")
        }

        // ByteArray 데이터를 File로 변환 (PCM 파일 생성)
        val pcmFile = File(context.filesDir, "recording.pcm")
        pcmFile.outputStream().use { outputStream ->
            byteArrayOutputStream?.writeTo(outputStream)
        }

        Log.d(Constant.TAG, "녹음 파일이 저장되었습니다: ${pcmFile.absolutePath}")

        // PCM 파일을 WAV 파일로 변환 후 반환
        val wavFile = File(context.filesDir, "recording.wav")
        saveToWav(wavFile)  // WAV로 변환
        return wavFile
    }


    private fun saveToWav(file: File) {
        val byteArray = byteArrayOutputStream?.toByteArray() ?: return

        // WAV 헤더를 포함한 PCM 데이터를 WAV 파일로 저장
        val wavFileOutputStream = FileOutputStream(file)
        val totalDataLen = byteArray.size + 36
        val byteRate = 16 * 44100 * 1 / 8 // 16-bit, Mono, 44100Hz

        // WAV 파일 헤더 작성
        val header = ByteBuffer.allocate(44)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put("RIFF".toByteArray())
            .putInt(totalDataLen)
            .put("WAVE".toByteArray())
            .put("fmt ".toByteArray())
            .putInt(16) // PCM header size
            .putShort(1) // format (1 for PCM)
            .putShort(1) // channels (Mono)
            .putInt(44100) // sample rate
            .putInt(byteRate) // byte rate
            .putShort(2) // block align
            .putShort(16) // bits per sample
            .put("data".toByteArray())
            .putInt(byteArray.size)

        wavFileOutputStream.write(header.array())
        wavFileOutputStream.write(byteArray)
        wavFileOutputStream.close()
    }

    fun stopRecording() {
        isRecording = false
    }

    fun pauseRecording() {
        isPaused = true
    }

    fun resumeRecording() {
        isPaused = false
    }
}
