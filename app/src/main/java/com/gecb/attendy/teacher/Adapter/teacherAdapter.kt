package com.gecb.attendy.teacher.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.gecb.attendy.R
import com.gecb.attendy.teacher.SubData
import com.gecb.attendy.teacher.TeacherFrag
import com.gecb.attendy.teacher.Teacher_home

class teacherAdapter(private val context: TeacherFrag, var subData: MutableList<SubData>) : RecyclerView.Adapter<teacherAdapter.ViewHolder>() {

    var list_subjects= mutableListOf<SubData>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sub=itemView.findViewById<TextView>(R.id.textView2)
        val branch=itemView.findViewById<TextView>(R.id.branch)
        val sem=itemView.findViewById<TextView>(R.id.sem)
        val tick=itemView.findViewById<ImageView>(R.id.tick)
        val layout=itemView.findViewById<ConstraintLayout>(R.id.layout)
        fun bind(subData: SubData){
            sub.text=subData.sub
            branch.text=subData.branch
            sem.text=subData.sem
            tick.visibility=View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View=LayoutInflater.from(context.context).inflate(R.layout.sub_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subdata:SubData=subData[position]
        holder.bind(subdata)
        holder.layout.setOnClickListener{
            if(holder.tick.isVisible) {
                holder.tick.visibility = View.GONE
                list_subjects.remove(subdata)
            }
            else{
                holder.tick.visibility=View.VISIBLE
                list_subjects.add(subdata)
            }
        }
    }

    fun get_selected_sub():MutableList<SubData>{
      return list_subjects
    }

    override fun getItemCount(): Int {
        return subData.size
    }
}