package com.gecb.attendy.teacher

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gecb.attendy.Splash
import com.gecb.attendy.databinding.FragmentTeacherHomeBinding
import com.gecb.attendy.teacher.Adapter.SubAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class Teacher_home : Fragment() {

    private var contextNullSafe: Context? = null
    lateinit var ref: DatabaseReference
    var auth: FirebaseAuth?=null
    lateinit var adapter: SubAdapter
    var user: FirebaseUser?=null
    var list_subjects= mutableListOf<SubData>()
    lateinit var _binding:FragmentTeacherHomeBinding
    lateinit var sub_ref:DatabaseReference
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTeacherHomeBinding.inflate(inflater,container,false)
        if (contextNullSafe == null) getContextNullSafety()

        auth=FirebaseAuth.getInstance()
        user=auth!!.currentUser

        ref= FirebaseDatabase.getInstance().reference.child("users").child(user!!.uid)
        sub_ref= FirebaseDatabase.getInstance().reference.child("Subjects")
        get_user()
        adapter=SubAdapter(this,list_subjects)
        _binding.rv.apply {
            val mManager = LinearLayoutManager(getContextNullSafety())
            setItemViewCacheSize(500)
            setDrawingCacheEnabled(true)
            setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH)
            setLayoutManager(mManager)
        }
        _binding.imageView.setOnClickListener{
            auth!!.signOut()
            val intent = Intent(getContextNullSafety(), Splash::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        _binding.profileImage.setOnClickListener{
            val intent = Intent(getContextNullSafety(),profile::class.java)
            startActivity(intent)
        }
        return binding.root
    }
    private fun get_user() {
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user_data=UserData("Welcome, "+snapshot.child("name").value + "✌️",
                    user!!.photoUrl.toString()
                )
                _binding.user=user_data
                if(snapshot.child("Lectures").exists()) {
                    snapshot.child("Lectures").children.forEach {
                        Log.e("branch","${it.value}")
                        val userModel: SubData? = it.getValue(SubData::class.java)
                        list_subjects.add(userModel!!)
                    }
                }
                adapter=SubAdapter(this@Teacher_home,list_subjects)
                _binding.rv.adapter=adapter
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Database Issue.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**CALL THIS IF YOU NEED CONTEXT */
    fun getContextNullSafety(): Context? {
        if (context != null) return context
        if (activity != null) return activity
        if (contextNullSafe != null) return contextNullSafe
        if (view != null && requireView().context != null) return requireView().context
        if (requireContext() != null) return requireContext()
        if (requireActivity() != null) return requireActivity()
        return if (requireView() != null && requireView().context != null) requireView().context else null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        contextNullSafe = context
    }
}