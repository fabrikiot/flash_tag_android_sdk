package com.intellicar.bletag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.intellicar.bletag.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        B7178A7667E7C9C0 ---- DeviceId Intellicar bluetooth tag
        binding.testBtnStart.setOnClickListener {

            if (binding.deviceId.text.toString().isEmpty()){
                runOnUiThread {

                    Toast.makeText(this, "Please enter Device Id", Toast.LENGTH_SHORT).show()

                }
            }

            FlashTAG.start(binding.deviceId.text.toString(), 5000L)

        }

        binding.testBtnStop.setOnClickListener {

            FlashTAG.stop()

            binding.tagId.text = ""
            binding.temperature.text = ""
            binding.humidity.text = ""
//            binding.magDet.text = ""
//            binding.movDet.text = ""
            binding.light.text = ""
            binding.voltage.text = ""
            binding.upTime.text = ""

        }

        FlashTAG.getContext(this)

        FlashTAG.callbackHandler = { resultJson ->

            val data = resultJson.getJSONObject("data")
            println("Data_In_FrontEnd:::$data")
            println("****************************")
            println("****************************")
            println("****************************")
            println("****************************")
            println("****************************")

            val tagId = data.getString("tagId")
            val temp = (data.getString("temperature").toFloat() / 100)
            val humidity = (data.getString("humidity").toFloat() / 100)
            val magDet = data.getBoolean("magDet")
            val movDet = data.getBoolean("movDet")
            val light = data.getString("light")
            val voltage = (data.getString("voltage").toFloat() / 10000)
            val uptime = data.getString("upTime")

            binding.tagId.text = tagId
            binding.temperature.text = temp. toString()
            binding.humidity.text = humidity.toString()
//            binding.magDet.text = magDet
//            binding.movDet.text = movDet
            if (movDet){
                binding.movDetImg.setImageResource(R.drawable.ic_tick)
            }else{
                binding.movDetImg.setImageResource(R.drawable.ic_close)
            }

            if (magDet){
                binding.magDetImg.setImageResource(R.drawable.ic_tick)
            }else{
                binding.magDetImg.setImageResource(R.drawable.ic_close)
            }
            binding.light.text = light
            binding.voltage.text = voltage.toString()
            binding.upTime.text = uptime

        }

    }
}