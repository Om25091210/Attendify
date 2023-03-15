package com.gecb.attendy

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.gecb.attendy.teacher.SubData
import com.gecb.attendy.teacher.TeacherFrag
import com.gecb.attendy.teacher.Teacher_home
import com.gecb.attendy.teacher.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var ref: DatabaseReference
    var auth:FirebaseAuth?=null
    var user: FirebaseUser?=null
    var list_subjects= mutableListOf<SubData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor =
            ContextCompat.getColor(this, R.color.green_300)
        window.navigationBarColor =
            ContextCompat.getColor(this, R.color.green_300)

        auth= FirebaseAuth.getInstance()
        user=auth!!.currentUser

        ref= FirebaseDatabase.getInstance().reference.child("users").child(user!!.uid)
        get_user()

    }
    private fun get_user() {
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.child("Lectures").exists()) {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.add(R.id.container, Teacher_home(), "homeFrag").commit()
                }else{
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.add(R.id.container, TeacherFrag(), "mainFrag").commit()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Database Issue.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}