package com.example.chatapp.ui.adapter

import android.annotation.SuppressLint
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SetTextI18n")
@BindingAdapter("formattedTime")
fun setFormattedTime(view: AppCompatTextView, timestamp: Long?) {
    timestamp?.let {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date = Date(it)
        view.text = sdf.format(date)
    } ?: run {
        view.text = "Unknown"
    }
}
