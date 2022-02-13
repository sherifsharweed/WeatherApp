package com.shekoo.iweather.ui.alert

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.shekoo.iweather.databinding.ActivityAddAlertBinding
import java.util.*

class AddAlertActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddAlertBinding
     var tHour : Int =0
     var tMinut : Int =0
    val calendar = Calendar.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectFirstTime.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                setTime(binding.firstTime)
            }
        })

        binding.selectSecondTime.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                setTime(binding.secondTime)
            }
        })

        binding.selectFirstDate.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                setDate(binding.firstDate)
            }
        })

        binding.selectSecondDate.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                setDate(binding.secondDate)
            }
        })
    }

    fun setTime(tvTime: TextView) {
        val timePickerDialog = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
                 tHour = hour
                 tMinut = minute
                val calendar2 = Calendar.getInstance()
                calendar2.set(0, 0, 0, tHour, tMinut)
                tvTime.setText(DateFormat.format("hh:mm aa", calendar2))
            }
        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false )
        timePickerDialog.updateTime(tHour, tMinut)
        timePickerDialog.show()
    }

    fun setDate(tvDate: TextView) {

        val datePickerDialog = DatePickerDialog(this, object : OnDateSetListener{
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    val month = month + 1
                    val date: String = dayOfMonth.toString() + "/" + month + "/" + year
                    tvDate.text = date
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()
    }
}