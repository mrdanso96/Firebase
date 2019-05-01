package com.example.firebase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class changeEmail : AppCompatActivity() {

    //declare variables
    var user_email:EditText?= null
    var user_password:EditText?= null
    var new_email:EditText?= null
    var update:Button?= null
    var firebaseAuth: FirebaseAuth?= null
    var user: FirebaseUser?= null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_email)

        //initialise variables
        user_email = findViewById(R.id.email_edit_text_change)
        user_password = findViewById(R.id.password_edit_text_change)
        new_email = findViewById(R.id.newemail_edit_text)
        update = findViewById(R.id.update_btn)
        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth!!.currentUser!!


        update?.setOnClickListener {

            UpdateEmail()

        }

    }

    //method for updating user email
    private fun UpdateEmail(){

        //get text from textfields
        var email = user_email?.text.toString().trim()
        var password = user_password?.text.toString().trim()
        var newemail = new_email?.text.toString().trim()

        //get users email and password
        var userInfo = EmailAuthProvider.getCredential(email,password)

        //reauthenticating current user to be able to update his email
        user?.reauthenticate(userInfo)?.addOnCompleteListener(object : OnCompleteListener<Void>{
            override fun onComplete(p0: Task<Void>) {

                //the users email with his new email
                user!!.updateEmail(newemail).addOnCompleteListener(object : OnCompleteListener<Void>{
                    override fun onComplete(task: Task<Void>) {

                        if (task.isSuccessful){

                            Toast.makeText(applicationContext,"Your Email has been Updated", Toast.LENGTH_SHORT).show()

                        }
                        else{

                            val error = task.exception?.message
                            Toast.makeText(applicationContext,"Error " + error,Toast.LENGTH_SHORT).show()

                        }

                    }


                })

            }

        })

    }

}
