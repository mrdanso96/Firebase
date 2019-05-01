package com.example.firebase

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class user_info : AppCompatActivity() {

    //declare variables
    var firstName:TextView?= null
    var lastName:TextView?= null
    var userName:TextView?= null
    var firebaseAuth:FirebaseAuth?= null
    var firebaseDb:DatabaseReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        //initialise variables
        firstName = findViewById(R.id.firstName_textview)
        lastName = findViewById(R.id.lastName_textview)
        userName = findViewById(R.id.userName_textview)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDb = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseAuth?.currentUser!!.uid)

        firebaseDb?.addValueEventListener(object  : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {



            }

            override fun onDataChange(task: DataSnapshot) {

                if (task.exists()){

                    val firstname = task.child("firstName").value as String
                    val lastname = task.child("lastName").value as String
                    val username = task.child("userName").value as String

                    firstName?.text = firstname
                    lastName?.text = lastname
                    userName?.text = username

                }

            }

        })

    }

    //method for sending user to update user info activity
    public fun update(view:View){

        startActivity(Intent(this@user_info,profile_activity::class.java))

    }

    //method for sending user to change email activity
    public fun changeEmail(view:View){

        startActivity(Intent(this@user_info,changeEmail::class.java))

    }

}

