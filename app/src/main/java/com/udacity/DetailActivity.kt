package com.udacity

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        var body = intent.getStringExtra("body")
        var status = intent.getStringExtra("status")
        detailGlideId.setText(body)
        detailStatusId.setText(status)
        var notification: NotificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        )as NotificationManager

        notification.cancelAll()
        if(status=="success")
        {
            imageView2.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_alt_24)
        }
        else
        {
            imageView2.setImageResource(R.drawable.ic_baseline_sentiment_very_dissatisfied_24)
        }
        var intent = Intent(applicationContext, MainActivity::class.java)
        backButton.setOnClickListener(){
            startActivity(intent)
            this.finish()
        }

    }

}
