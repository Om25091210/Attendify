package com.gecb.attendy.teacher

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gecb.attendy.R
import com.gecb.attendy.databinding.FragmentTeacherBinding
import com.gecb.attendy.teacher.Adapter.teacherAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class TeacherFrag : Fragment() {

    private var contextNullSafe: Context? = null
    private var branch= mutableListOf<String>()
    private var sem= mutableListOf<String>()
    private var subData= mutableListOf<SubData>()
    private lateinit var _binding: FragmentTeacherBinding
    private val binding get() = _binding
    lateinit var adapter:teacherAdapter
    var auth:FirebaseAuth?=null
    var user:FirebaseUser?=null
    lateinit var ref:DatabaseReference
    lateinit var sub_ref:DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentTeacherBinding.inflate(inflater,container,false)
        if (contextNullSafe == null) getContextNullSafety()

        auth=FirebaseAuth.getInstance()
        user=auth!!.currentUser

        ref=FirebaseDatabase.getInstance().reference.child("users").child(user!!.uid)
        sub_ref=FirebaseDatabase.getInstance().reference.child("Subjects")
        get_user()

        _binding.branchLayout.csid=R.drawable.ic_cse
        _binding.branchLayout.itid=R.drawable.ic_it
        _binding.branchLayout.ettid=R.drawable.ic_ett
        _binding.branchLayout.eeid=R.drawable.ic_ee
        _binding.branchLayout.minid=R.drawable.ic_mining
        _binding.branchLayout.civilid=R.drawable.ic_civil
        _binding.branchLayout.mechid=R.drawable.ic_mech

        _binding.sem.oneid=R.drawable.ic_bird
        _binding.sem.twoid=R.drawable.ic_bird
        _binding.sem.threeid=R.drawable.ic_bird
        _binding.sem.fourthid=R.drawable.ic_bird
        _binding.sem.fiveid=R.drawable.ic_bird
        _binding.sem.sixid=R.drawable.ic_bird
        _binding.sem.sevenid=R.drawable.ic_bird
        _binding.sem.eightid=R.drawable.ic_bird
        _binding.sem.isVisible=true

        observe_branch_clicks()
        observe_sem_clicks()

        _binding.branchLayout.isVisible=true
        _binding.isVisibleBranch=true
        _binding.isVisibleClass=false
        _binding.isVisibleSem=false
        _binding.isVisibleSubjects=0
        _binding.branchLayout.proceed.setOnClickListener{
            _binding.branchLayout.isVisible=false
            _binding.isVisibleBranch=false
            _binding.isVisibleSem=true
        }
        _binding.sem.proceed.setOnClickListener{
            _binding.branchLayout.isVisible=false
            _binding.isVisibleBranch=false
            _binding.isVisibleClass=false
            _binding.isVisibleClass=true
            _binding.isVisibleSem=false
            fetch_subjects()
        }
        adapter=teacherAdapter(this,subData)
        _binding.rv.apply {
            val mManager = LinearLayoutManager(getContextNullSafety())
            setItemViewCacheSize(500)
            setDrawingCacheEnabled(true)
            setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH)
            setLayoutManager(mManager)
        }
        _binding.save.setOnClickListener{
            ref.child("Lectures").removeValue()
            if(adapter.get_selected_sub().size!=0){
                adapter.get_selected_sub().forEach{
                    ref.child("Lectures").child(
                        it.branch+"~"+it.sem+"~"+it.sub
                    ).setValue(it)
                }
            }
            _binding.save.visibility=View.GONE
            _binding.rv.visibility=View.GONE
            (getContextNullSafety() as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .add(R.id.container, Teacher_home())
                .addToBackStack(null)
                .commit()
        }
        _binding.rv.adapter=adapter
        return binding.root
    }

    private fun fetch_subjects() {
        sub_ref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    snapshot.children.forEach {
                        if (branch.contains(it.key)) {
                            it.key?.let { it1 ->
                                snapshot.child(it1).children.forEach { it2 ->
                                    if (sem.contains(it2.key)) {
                                        Log.e("Branch", it.key + "")
                                        Log.e("Sem", it2.key + "")
                                        snapshot.child(it1)
                                            .child(it2.key!!).children.forEach { it3 ->
                                            subData.add(SubData(it1, it2.key!!, it3.key!!))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                adapter=teacherAdapter(this@TeacherFrag,subData)
                _binding.rv.adapter=adapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Database Issue.", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun observe_sem_clicks() {
        binding.sem.one.setOnClickListener{
            if(sem.contains("1st")){
                _binding.sem.oneid=R.drawable.ic_bird
                sem.remove("1st")
            }
            else{
                _binding.sem.oneid=R.drawable.ic_tick
                sem.add("1st")
            }
        }
        binding.sem.two.setOnClickListener{
            if(sem.contains("2nd")){
                _binding.sem.twoid=R.drawable.ic_bird
                sem.remove("2nd")
            }
            else{
                _binding.sem.twoid=R.drawable.ic_tick
                sem.add("2nd")
            }
        }
        binding.sem.three.setOnClickListener{
            if(sem.contains("3rd")){
                _binding.sem.threeid=R.drawable.ic_bird
                sem.remove("3rd")
            }
            else{
                _binding.sem.threeid=R.drawable.ic_tick
                sem.add("3rd")
            }
        }
        binding.sem.four.setOnClickListener{
            if(sem.contains("4th")){
                _binding.sem.fourthid=R.drawable.ic_bird
                sem.remove("4th")
            }
            else{
                _binding.sem.fourthid=R.drawable.ic_tick
                sem.add("4th")
            }
        }
        binding.sem.five.setOnClickListener{
            if(sem.contains("5th")){
                _binding.sem.fiveid=R.drawable.ic_bird
                sem.remove("5th")
            }
            else{
                _binding.sem.fiveid=R.drawable.ic_tick
                sem.add("5th")
            }
        }
        binding.sem.six.setOnClickListener{
            if(sem.contains("6th")){
                _binding.sem.sixid=R.drawable.ic_bird
                sem.remove("6th")
            }
            else{
                _binding.sem.sixid=R.drawable.ic_tick
                sem.add("6th")
            }
        }
        binding.sem.seven.setOnClickListener{
            if(sem.contains("7th")){
                _binding.sem.sevenid=R.drawable.ic_bird
                sem.remove("7th")
            }
            else{
                _binding.sem.sevenid=R.drawable.ic_tick
                sem.add("7th")
            }
        }
        binding.sem.eight.setOnClickListener{
            if(sem.contains("8th")){
                _binding.sem.eightid=R.drawable.ic_bird
                sem.remove("8th")
            }
            else{
                _binding.sem.eightid=R.drawable.ic_tick
                sem.add("8th")
            }
        }
    }
    private fun observe_branch_clicks() {
        binding.branchLayout.cse.setOnClickListener{
            if(branch.contains("CSE")){
                _binding.branchLayout.csid=R.drawable.ic_cse
                branch.remove("CSE")
            }
            else{
                _binding.branchLayout.csid=R.drawable.ic_tick
                branch.add("CSE")
            }
        }
        binding.branchLayout.it.setOnClickListener{
            if(branch.contains("IT")){
                _binding.branchLayout.itid=R.drawable.ic_it
                branch.remove("IT")
            }
            else{
                _binding.branchLayout.itid=R.drawable.ic_tick
                branch.add("IT")
            }
        }
        binding.branchLayout.ett.setOnClickListener{
            if(branch.contains("ET&T")){
                _binding.branchLayout.ettid=R.drawable.ic_ett
                branch.remove("ET&T")
            }
            else{
                _binding.branchLayout.ettid=R.drawable.ic_tick
                branch.add("ET&T")
            }
        }
        binding.branchLayout.elec.setOnClickListener{
            if(branch.contains("Electrical")){
                _binding.branchLayout.eeid=R.drawable.ic_ee
                branch.remove("Electrical")
            }
            else{
                _binding.branchLayout.eeid=R.drawable.ic_tick
                branch.add("Electrical")
            }
        }
        binding.branchLayout.civil.setOnClickListener{
            if(branch.contains("Civil")){
                _binding.branchLayout.civilid=R.drawable.ic_civil
                branch.remove("Civil")
            }
            else{
                _binding.branchLayout.civilid=R.drawable.ic_tick
                branch.add("Civil")
            }
        }
        binding.branchLayout.mining.setOnClickListener{
            if(branch.contains("Mining")){
                _binding.branchLayout.minid=R.drawable.ic_mining
                branch.remove("Mining")
            }
            else{
                _binding.branchLayout.minid=R.drawable.ic_tick
                branch.add("Mining")
            }
        }
        binding.branchLayout.mech.setOnClickListener{
            if(branch.contains("Mechanical")){
                _binding.branchLayout.mechid=R.drawable.ic_mech
                branch.remove("Mechanical")
            }
            else{
                _binding.branchLayout.mechid=R.drawable.ic_tick
                branch.add("Mechanical")
            }
        }
    }

    private fun get_user() {
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user_data=UserData("Welcome, "+snapshot.child("name").value + "✌️",
                   user!!.photoUrl.toString()
                )
                _binding.user=user_data
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