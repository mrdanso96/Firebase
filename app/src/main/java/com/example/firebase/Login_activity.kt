package com.example.firebase

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Login_activity : AppCompatActivity() {

    //declare variables as private as only used in this activity
    private var login_btn: Button?= null
    private var user_email: EditText?= null
    private var user_password: EditText?= null
    private var firebaseAuth: FirebaseAuth?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_activity)

        //initialise variables
        login_btn = findViewById(R.id.login_btn_activity)
        user_email = findViewById(R.id.user_email_login)
        user_password = findViewById(R.id.user_password_login)
        firebaseAuth = FirebaseAuth.getInstance()

        login_btn?.setOnClickListener {

            LoginTheUser()

        }


    }

    private fun  LoginTheUser(){

        //fetching text from email and password fields and storing in variables
        var email_text = user_email?.text.toString().trim()
        var password_text = user_password?.text.toString().trim()

        //checking if email field is empty
        if (TextUtils.isEmpty(email_text)){

            Toast.makeText(applicationContext,"Email Field cannot be Empty", Toast.LENGTH_SHORT).show()

        }
        //checking if password field is empty
        else if (TextUtils.isEmpty(password_text)){

            Toast.makeText(applicationContext,"Password Field cannot be Empty", Toast.LENGTH_SHORT).show()

        }
        //login user
        else{

            firebaseAuth?.signInWithEmailAndPassword(email_text,password_text)?.addOnCompleteListener(object : OnCompleteListener<AuthResult>{
                override fun onComplete(task: Task<AuthResult>) {

                    if (task.isSuccessful){

                        Toast.makeText(applicationContext,"Login Successful",Toast.LENGTH_SHORT).show()

                        val user:FirebaseUser = firebaseAuth!!.currentUser!!

                        if (user.isEmailVerified){

                            startActivity(Intent(this@Login_activity,TestActivity::class.java))

                        }
                        else{

                            Toast.makeText(applicationContext,"Account Not Verified",Toast.LENGTH_SHORT).show()

                        }

                    }
                    else{

                        //getting error and toasting it
                        val error = task.exception?.message
                        Toast.makeText(applicationContext,"Error " + error,Toast.LENGTH_SHORT).show()

                    }

                }


            })

        }

    }

     fun reset(view:View){

         startActivity(Intent(this@Login_activity, password_rest::class.java))

    }


}
