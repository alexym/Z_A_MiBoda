package com.alexym.android.zamiboda.ui.home

import android.icu.text.SimpleDateFormat
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexym.android.zamiboda.constants.Constants
import java.util.*

class HomeViewModel : ViewModel() {
    lateinit var countdown_timer: CountDownTimer
    var initializeKonfettiView = true

    private val _days = MutableLiveData<String>()
    val days: LiveData<String> = _days

    private val _hours = MutableLiveData<String>()
    val hours: LiveData<String> = _hours

    private val _minutes = MutableLiveData<String>()
    val minutes: LiveData<String> = _minutes

    private val _seconds = MutableLiveData<String>()
    val seconds: LiveData<String> = _seconds

    val _isToday = MutableLiveData<Boolean>()
    val isToday: LiveData<Boolean> = _isToday

    init {
        val currentTime = Calendar.getInstance().time
        val format1 = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
        val endDate = format1.parse(Constants.EVENT_DATE_TIME)
        var different = endDate.time - currentTime.time
        countdown_timer = object : CountDownTimer(different, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var diff = millisUntilFinished
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val daysInMilli = hoursInMilli * 24

                val elapsedDays = diff / daysInMilli
                diff %= daysInMilli

                val elapsedHours = diff / hoursInMilli
                diff %= hoursInMilli

                val elapsedMinutes = diff / minutesInMilli
                diff %= minutesInMilli

                val elapsedSeconds = diff / secondsInMilli

                _days.value = "$elapsedDays"
                if (initializeKonfettiView) {
                    initializeKonfettiView = false
                    _isToday.value = elapsedDays == 0L
                }
                _hours.value = "$elapsedHours"
                _minutes.value = "$elapsedMinutes"
                _seconds.value = "$elapsedSeconds"
            }

            override fun onFinish() {

            }
        }
        countdown_timer.start()

    }
}