package com.gecb.attendy.teacher.Adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.gecb.attendy.R
import com.gecb.attendy.teacher.Calendar_Attendance
import com.gecb.attendy.teacher.SubData
import com.gecb.attendy.teacher.Teacher_home

class SubAdapter(private val context: Teacher_home, var subData: MutableList<SubData>) : RecyclerView.Adapter<SubAdapter.ViewHolder>() {

    var list_subjects= mutableListOf<SubData>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sub=itemView.findViewById<TextView>(R.id.textView84)
        val branch=itemView.findViewById<TextView>(R.id.textView86)
        val sem=itemView.findViewById<TextView>(R.id.textView85)
        val img=itemView.findViewById<TextView>(R.id.head1)
        val view=itemView.findViewById<TextView>(R.id.view)
        fun bind(subData: SubData){
            sub.text=subData.sub
            branch.text=subData.branch
            sem.text=subData.sem
            img.text=subData.sub!!.subSequence(0,2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context.context).inflate(R.layout.subject_attendance,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subdata: SubData =subData[position]
        holder.bind(subdata)
        holder.view.setOnClickListener{
            val bundle=Bundle()
            bundle.putString("subject",list_subjects.get(position).sub)
            bundle.putString("branch",list_subjects.get(position).branch)
            bundle.putString("semester",list_subjects.get(position).sem)
            val calatten=Calendar_Attendance()
            calatten.arguments=(bundle)
            (context as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .add(R.id.layout, calatten)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return subData.size
    }
}