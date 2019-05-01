package com.example.firebase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class password_rest : AppCompatActivity() {

    //declare variables
    private var user_email:EditText?=null
    private var button_reset:Button?=null
    private var firebase:FirebaseAuth?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_rest)

        user_email = findViewById(R.id.user_email_reset)
        button_reset = findViewById(R.id.reset_btn)
        firebase = FirebaseAuth.getInstance()

        button_reset?.setOnClickListener {

            ResetPassword()

        }

    }

   private fun ResetPassword(){

       val email = user_email?.text.toString().trim()

       if (TextUtils.isEmpty(email)){

           Toast.makeText(applicationContext,"Please Enter your email", Toast.LENGTH_SHORT).show()

       }
       else{

           firebase?.sendPasswordResetEmail(email)?.addOnCompleteListener(object : OnCompleteListener<Void>{
               override fun onComplete(task: Task<Void>) {


                   if (task.isSuccessful){

                       Toast.makeText(applicationContext,"Please Check Your Email For Reset Password",Toast.LENGTH_SHORT).show()
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

}
