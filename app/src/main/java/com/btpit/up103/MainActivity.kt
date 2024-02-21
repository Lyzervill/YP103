package com.btpit.up103

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var countLike = 0
    private var countShare = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var textView6 = findViewById<TextView>(R.id.textView6)
        var likesTextView = findViewById<TextView>(R.id.textView10)
        var viewsTextView = findViewById<TextView>(R.id.textView7)
        var y = viewsTextView.text.toString().toInt()
        y++
        viewsTextView.text = y.toString()
        countLike = textView6.text.toString().toInt()
        var bs = true
        findViewById<ImageButton>(R.id.imageButtonLike).setOnClickListener {
            if(bs) {
                countLike++
                bs = false
            }
            else{
                countLike--
                bs = true

            }
            textView6.text=amountToStr(countLike)
        }
        findViewById<ImageButton>(R.id.imageButtonShare).setOnClickListener {
            countShare++
            likesTextView.text = amountToStr(countShare)
        }

    }
        fun amountToStr(amount:Int): String {
            return when(amount)
            {
                in 0..<1_000 -> amount.toString()
                in 1_000..<1_000_000 -> ((amount/100).toFloat()/10).toString()+"K"
                in 1_000_000..<1_000_000_000 -> ((amount/100_000).toFloat()/10).toString()+"M"
                else -> "более млрд"
            }
        }

}