package com.example.firebase

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)



    }

    fun changeEmail_btn(view:View){

        startActivity(Intent(this@TestActivity,changeEmail::class.java))

    }

    fun profile(view:View){

        startActivity(Intent(this@TestActivity,profile_activity::class.java))

    }

    fun info(view: View){

        startActivity(Intent(this@TestActivity,user_info::class.java))

    }

    fun image(view: View){

        startActivity(Intent(this@TestActivity,firebase_storage::class.java))

    }
}
