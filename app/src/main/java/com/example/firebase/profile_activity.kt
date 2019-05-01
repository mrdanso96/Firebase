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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class profile_activity : AppCompatActivity() {

    //declare variables
    var user_firstName:EditText?= null
    var user_lastName:EditText?= null
    var userName:EditText?= null
    var update_btn:Button?= null
    var firebaseAuth:FirebaseAuth?= null
    var firebaseDb:DatabaseReference?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_activity)

        user_firstName = findViewById(R.id.user_first_name)
        user_lastName = findViewById(R.id.user_last_name)
        userName = findViewById(R.id.user_username)
        update_btn = findViewById(R.id.userinfo_update_btn)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDb = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseAuth?.currentUser!!.uid)


        update_btn?.setOnClickListener {

            SaveUserInfo()

        }

    }

    //method for storing user info
    private fun SaveUserInfo(){

        val firstname = user_firstName?.text.toString().trim()
        val lastname = user_lastName?.text.toString().trim()
        val username = userName?.text.toString().trim()

        if (TextUtils.isEmpty(firstname)){

            Toast.makeText(applicationContext,"First name field cannot be empty",Toast.LENGTH_SHORT).show()

        }
        else if (TextUtils.isEmpty(lastname)){

            Toast.makeText(applicationContext,"Last name field cannot be empty",Toast.LENGTH_SHORT).show()

        }
        else if (TextUtils.isEmpty(username)){

            Toast.makeText(applicationContext,"Username field cannot be empty",Toast.LENGTH_SHORT).show()

        }
        else{


            val userinfo = HashMap<String,Any>()
            userinfo.put("firstName",firstname)
            userinfo.put("lastName",lastname)
            userinfo.put("userName",username)

            //uploading user info into firebase database
            firebaseDb?.updateChildren(userinfo)?.addOnCompleteListener(object : OnCompleteListener<Void>{
                override fun onComplete(task: Task<Void>) {

                    if (task.isSuccessful){

                        Toast.makeText(applicationContext,"Your information was updated",Toast.LENGTH_SHORT).show()

                    }
                    else{

                        val error = task.exception?.message
                        Toast.makeText(applicationContext,"Error "+ error,Toast.LENGTH_SHORT).show()

                    }

                }


            })

        }

    }

}
