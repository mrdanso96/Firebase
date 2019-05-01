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

class MainActivity : AppCompatActivity() {

    //declare variables as private as only used in this activity
    private var signup_btn:Button?= null
    private var user_email_editText:EditText?= null
    private var user_password_editText:EditText?= null
    private var firebaseAuth:FirebaseAuth?= null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialise variables
        signup_btn = findViewById(R.id.signup_btn)
        user_email_editText = findViewById(R.id.user_email_reset)
        user_password_editText = findViewById(R.id.user_password)
        firebaseAuth = FirebaseAuth.getInstance()

        signup_btn?.setOnClickListener {

            //all code for creating new user
            RegisterNewUser()

        }


    }

    private fun RegisterNewUser(){

        //fetching text from email and password fields and storing in variables
        var email_text = user_email_editText?.text.toString().trim()
        var password_text = user_password_editText?.text.toString().trim()

        //checking if email field is empty
        if (TextUtils.isEmpty(email_text)){

            Toast.makeText(applicationContext,"Email Field cannot be Empty",Toast.LENGTH_SHORT).show()

        }
        //checking if password field is empty
        else if (TextUtils.isEmpty(password_text)){

            Toast.makeText(applicationContext,"Password Field cannot be Empty",Toast.LENGTH_SHORT).show()

        }
        //creating a new user
        else{
            firebaseAuth?.createUserWithEmailAndPassword(email_text,password_text)?.addOnCompleteListener(object : OnCompleteListener<AuthResult>{
                override fun onComplete(task: Task<AuthResult>) {

                    if (task.isSuccessful){

                        Toast.makeText(applicationContext,"Account Created",Toast.LENGTH_SHORT).show()

                       //getting current user
                        val user: FirebaseUser = firebaseAuth!!.currentUser!!

                        //sending user an email to verify their email
                        user.sendEmailVerification().addOnCompleteListener(object : OnCompleteListener<Void>{
                            override fun onComplete(task: Task<Void>) {

                                if (task.isSuccessful){

                                    Toast.makeText(applicationContext,"Please Check your Email",Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@MainActivity,Login_activity::class.java))

                                }
                                else{

                                    val error = task.exception?.message
                                    Toast.makeText(applicationContext,"Error " + error,Toast.LENGTH_SHORT).show()

                                }

                            }

                        })

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

    public fun Login(view:View){

        startActivity(Intent(this@MainActivity,Login_activity::class.java))

    }

}
