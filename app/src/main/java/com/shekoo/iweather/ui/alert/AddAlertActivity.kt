package com.shekoo.iweather.ui.alert

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.shekoo.iweather.databinding.ActivityAddAlertBinding
import com.shekoo.iweather.model.MyAlert
import com.shekoo.iweather.ui.TAG
import java.util.*
import java.util.concurrent.TimeUnit

class AddAlertActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddAlertBinding
    private lateinit var alertViewModel: AlertViewModel
    var fHour : Int =0
    var fMinute : Int =0
    var lHour : Int =0
    var lMinute : Int =0
    var fDay : Int =0
    var fMonth : Int =0
    var fYear : Int =0
    var lDay : Int =0
    var lMonth : Int =0
    var lYear : Int =0


    val calendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val alertViewModelFactory = AlertViewModelFactory(applicationContext)
        alertViewModel = ViewModelProvider(this,alertViewModelFactory).get(AlertViewModel::class.java)

        binding.selectFirstTime.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                setFirstTime(binding.firstTime)


            }
        })

        binding.selectSecondTime.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                setSecondTime(binding.secondTime)
            }
        })

        binding.selectFirstDate.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                setFirstDate(binding.firstDate)
            }
        })

        binding.selectSecondDate.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                setSecondDate(binding.secondDate)
            }
        })

        binding.btnSave.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val firstUNIX = getUnix(fYear, fMonth, fDay, fHour, fMinute)
                val secondUNIX = getUnix(lYear, lMonth, lDay, lHour, lMinute)

                val calendarnow = Calendar.getInstance()
                val calendarMillies = calendarnow.timeInMillis / 1000L

                if (firstUNIX < calendarMillies || secondUNIX < calendarMillies) {
                    Toast.makeText(applicationContext, "Error in selecting date", Toast.LENGTH_SHORT).show()
                } else {
                    alertViewModel.insertAlarmItem(MyAlert(firstUNIX, secondUNIX))

                    if (binding.notificationButton.isChecked) {
                        setWorkNotification(firstUNIX)
                    } else {
                        setWorkAlarm(firstUNIX)
                    }
                    finish()
                }
            }
        })
    }


    fun setFirstTime(tvTime: TextView) {
        val timePickerDialog = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
                fHour = hour
                fMinute = minute
                val calendar2 = Calendar.getInstance()
                calendar2.set(0, 0, 0, fHour, fMinute)
                tvTime.setText(DateFormat.format("hh:mm aa", calendar2))
            }
        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false )
        timePickerDialog.updateTime(fHour, fMinute)
        timePickerDialog.show()
    }

    fun setSecondTime(tvTime: TextView) {
        val timePickerDialog = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
                lHour = hour
                lMinute = minute
                val calendar2 = Calendar.getInstance()
                calendar2.set(0, 0, 0, lHour, lMinute)
                tvTime.setText(DateFormat.format("hh:mm aa", calendar2))
            }
        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false )
        timePickerDialog.updateTime(lHour, lMinute)
        timePickerDialog.show()
    }

    fun setFirstDate(tvDate: TextView) {

        val datePickerDialog = DatePickerDialog(this, object : OnDateSetListener{
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    fYear = year
                    fMonth = month+1
                    fDay = dayOfMonth
                    val date: String = fDay.toString() + "/" + fMonth + "/" + fYear
                    tvDate.text = date
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()
    }

    fun setSecondDate(tvDate: TextView) {

        val datePickerDialog = DatePickerDialog(this, object : OnDateSetListener{
            override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                lYear = year
                lMonth = month+1
                lDay = dayOfMonth
                val date: String = lDay.toString() + "/" + lMonth + "/" + lYear
                tvDate.text = date
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()
    }

    fun getUnix(year : Int , month : Int , day : Int , hour : Int , minute : Int ): Long{

        val cal = Calendar.getInstance()
        cal.set(year,month-1,day,hour,minute,0)
        return (cal.timeInMillis/1000L)
    }

    private fun setWorkAlarm( UNIX: Long) {
        val dataInput = Data.Builder().putLong("KEY", UNIX).build()
        Log.i(TAG, "setWorkAlarm: "+dataInput)
        val workManager : WorkManager = WorkManager.getInstance()
        val calendarNow = Calendar.getInstance()
        val nowMillis = calendarNow.timeInMillis
        val diff = UNIX*1000L - nowMillis
        Log.i(TAG, "setWorkAlarm: $diff")
        val workRequest : WorkRequest = OneTimeWorkRequest.Builder(WorkManagerForAlarm::class.java)
            .setInitialDelay(diff, TimeUnit.MILLISECONDS)
            .setInputData(dataInput)
            .build()
        workManager.enqueue(workRequest)
    }

    private fun setWorkNotification( UNIX: Long) {
        val dataInput = Data.Builder().putLong("KEY", UNIX).build()
        val workManager : WorkManager = WorkManager.getInstance()
        val calendarNow = Calendar.getInstance()
        val nowMillis = calendarNow.timeInMillis
        val diff = UNIX*1000L - nowMillis
        Log.i(TAG, "setWorkAlarm: $diff")
        val workRequest : WorkRequest = OneTimeWorkRequest.Builder(WorkManagerForNotification::class.java)
            .setInputData(dataInput)
            .setInitialDelay(diff, TimeUnit.MILLISECONDS).build()
        workManager.enqueue(workRequest)
    }
}