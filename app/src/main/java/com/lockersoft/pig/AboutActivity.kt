package com.lockersoft.pig

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class AboutActivity : AppCompatActivity() {
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        imageView = findViewById(R.id.imageView)
    }

    fun imgViewOnClick(vw: View) {
        val intent = Intent( this, MainActivity::class.java)
        startActivity(intent)
    }
    fun btnSupportOnClick(vw: View) {
        val uri: Uri = Uri.parse("https://www.lockersoft.com")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
    fun btnAboutOnClick(vw: View) {
        val uri: Uri = Uri.parse("https://www.lockersoft.com/webpages/about.php")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

}