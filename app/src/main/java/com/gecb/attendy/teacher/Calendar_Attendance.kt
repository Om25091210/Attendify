package com.gecb.attendy.teacher

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alpha.mylibrary.RobotoCalendarView
import com.gecb.attendy.R
import com.google.firebase.database.*
import java.util.*


class Calendar_Attendance : Fragment(), RobotoCalendarView.RobotoCalendarListener,LocationListener {

    var robotoCalendarView: RobotoCalendarView? = null
    var contextNullSafe: Context? = null
    var search: EditText? = null
    var stat_name: String? = null
    var recyclerView: RecyclerView? = null
    lateinit var ref: Query

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_calendar__attendance, container, false)
        if (contextNullSafe == null) getContextNullSafety()
        robotoCalendarView = view.findViewById(R.id.roboto)
        search = view.findViewById<EditText>(R.id.search)
        recyclerView = view.findViewById<RecyclerView>(R.id.cal_rv)
        val mManager = LinearLayoutManager(getContextNullSafety())
        recyclerView!!.setItemViewCacheSize(500)
        recyclerView!!.setDrawingCacheEnabled(true)
        recyclerView!!.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH)
        recyclerView!!.setLayoutManager(mManager)
        ref= FirebaseDatabase.getInstance().reference.child("users").orderByChild("role").equalTo("student")
        // Set listener, in this case, the same activity
        // Set listener, in this case, the same activity
        robotoCalendarView!!.setRobotoCalendarListener(this)

        robotoCalendarView!!.setShortWeekDays(false)

        robotoCalendarView!!.showDateTitle(true)

        robotoCalendarView!!.setDate(Date())
        search!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                search(s.toString() + "")
            }
        })
        return view
    }

    private fun search(s: String) {
        TODO("Not yet implemented")
    }

    override fun onDayClick(date: Date?) {
        get_data()
    }

    private fun get_data() {
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {

                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Error","DatabaseError")
            }
        })
    }

    override fun onDayLongClick(date: Date?) {
        TODO("Not yet implemented")
    }

    override fun onRightButtonClick() {
        TODO("Not yet implemented")
    }

    override fun onLeftButtonClick() {
        TODO("Not yet implemented")
    }

    override fun onLocationChanged(p0: Location) {
        TODO("Not yet implemented")
    }
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