package com.example.pr20


import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun threadStart():Runnable{
        val runnable = Runnable {
            val textViewGyroscope: TextView = findViewById(R.id.textViewGyroscope)
            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            val sensorEventListener = object : SensorEventListener {
                override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
                override fun onSensorChanged(event: SensorEvent) {
                    val value = event?.values
                    textViewGyroscope.text = "Ось X: ${value?.get(0)?.toInt()}" +
                            "\nОсь Y: ${value?.get(1)?.toInt()}" +
                            "\nОсь Z: ${value?.get(2)?.toInt()}"
                }
            }
            sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        return runnable
    }
    val thread = Thread(threadStart())
    var isOn=true

    fun buttonThread_Click(view: View) {
        if (isOn) {
            isOn=false
            val buttonThread: Button =findViewById(R.id.buttonThread)
            buttonThread.setText(R.string.endThread)
            thread.start()
            Thread.sleep(500)
        }
        else {
            isOn=true
            val textViewGyroscope: TextView = findViewById(R.id.textViewGyroscope)
            textViewGyroscope.text="Поток остановлен"
            val buttonThread:Button=findViewById(R.id.buttonThread)
            buttonThread.setText(R.string.startThread)
            thread.interrupt()
        }
    }
}
