package com.example.firebase

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException
import java.util.*

class firebase_storage : AppCompatActivity() {

    //declare variables
    var upload_btn:Button?= null
    var image_View:ImageView?= null
    var imageUri:Uri?= null
    private var storage:FirebaseStorage?= null
    private var imageRef:StorageReference?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_storage)

        //initialise variables
        upload_btn = findViewById(R.id.upload_image_btn)
        image_View = findViewById(R.id.imageView)
        storage = FirebaseStorage.getInstance()
        imageRef = storage?.reference?.child("Images")

        image_View?.setOnClickListener {

            PickImageFromGallery()

        }

        upload_btn?.setOnClickListener {

            AddImageToFirebase()

        }

    }

    //selecting image from gallery
    private fun PickImageFromGallery(){

        val gallery = Intent()
        gallery.type = "image/*"
        gallery.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(gallery,111)

    }

    //setting image view to selected image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null){

            imageUri = data.data

            try {

                val image = MediaStore.Images.Media.getBitmap(contentResolver,imageUri)
                image_View?.setImageBitmap(image)

            }
            catch (error:IOException){

                Toast.makeText(applicationContext,"Error "+ error,Toast.LENGTH_SHORT).show()

            }

        }

    }

    private fun AddImageToFirebase(){

        if (imageUri != null){

            //give image a random id
            val ref = imageRef?.child(UUID.randomUUID().toString())
            ref?.putFile(imageUri!!)?.addOnCompleteListener(object : OnCompleteListener<UploadTask.TaskSnapshot>{
                override fun onComplete(task: Task<UploadTask.TaskSnapshot>) {

                    if (task.isSuccessful){

                        Toast.makeText(applicationContext,"Image Uploaded",Toast.LENGTH_SHORT).show()

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
