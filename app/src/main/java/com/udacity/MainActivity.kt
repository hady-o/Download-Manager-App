package com.udacity

import android.app.DownloadManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0
    var selectedUri = ""
    var notBody=""
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action
    private var notStatus=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        createChannel(application  ,getString(R.string.not_channel_id),getString(R.string.not_channel))





        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))


        custom_button.setOnClickListener {
           // download()
            var id = groupId.checkedRadioButtonId
            if(id==R.id.glideId)
            {
                selectedUri = glide
                custom_button.buttonState = ButtonState.Loading
                notBody = getString(R.string.glide_by_bumptech)
                download()
            }
            else if(id == R.id.retrofitId)
            {
                selectedUri = retrofiit
                custom_button.buttonState = ButtonState.Loading
                notBody = getString(R.string.retrofit_by_square)
                download()
            }
            else if(id == R.id.loadAppId)
            {
                selectedUri = udacity
                custom_button.buttonState = ButtonState.Loading
                notBody = getString(R.string.load_app_by_udacity)
                download()
            }
            else
            {
                Toast.makeText(applicationContext,"please select uri",Toast.LENGTH_LONG).show()
            }



        }
    }

    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            var notification: NotificationManager = ContextCompat.getSystemService(
                applicationContext,
                NotificationManager::class.java
            )as NotificationManager
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            val download = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            val cursor: Cursor =
                download.query(DownloadManager.Query().setFilterById(id!!))

            if (cursor.moveToNext()) {
                val req = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

                if (req == DownloadManager.STATUS_SUCCESSFUL)
                {
                    notStatus = "success"

                }
                else
                {
                    notStatus = "failed"

                }
                notification.sendNotification(notBody,context!!,notStatus)

            }
            custom_button.buttonState = ButtonState.Completed
        }

    }

    private fun download() {
        val request =
            DownloadManager.Request(Uri.parse(selectedUri))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.
    }

    companion object {
        private const val udacity =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val glide =
            "https://github.com/bumptech/glide"
        private const val retrofiit =
            "https://github.com/square/retrofit "
        private const val CHANNEL_ID = "channelId"
    }



}
