package com.gecb.attendy.teacher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.gecb.attendy.MainActivity
import com.gecb.attendy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text
import soup.neumorphism.NeumorphCardView

class profile : AppCompatActivity() {

    lateinit var binding : profile

    var name = ""
    var email = ""
    lateinit var save_btn : LottieAnimationView

    var one_sem = false
    var two_sem = false
    var three_sem = false
    var four_sem = false
    var five_sem = false
    var six_sem = false
    var seven_sem = false
    var eight_sem = false

    var cse = false
    var It = false
    var etnt = false
    var mining = false
    var electrical = false
    var mechanical = false
    var civil = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        findViewById<NeumorphCardView>(R.id.back).setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })

        observe_sem()
        observe_branch()


        save_btn = findViewById(R.id.upload)
        save_btn.setOnClickListener(View.OnClickListener {
            if(validate())
            {
                var user = FirebaseAuth.getInstance().currentUser?.uid
                var ref= FirebaseDatabase.getInstance().reference.child("users").child(user!!)
            }
        })

    }




    private fun observe_branch() {

        findViewById<TextView>(R.id.profile_cse).setOnClickListener(View.OnClickListener { cse = !cse
            if(cse)
                findViewById<TextView>(R.id.profile_cse).setBackgroundResource(R.drawable.stroke_bg)
            else
                findViewById<TextView>(R.id.profile_cse).setBackgroundResource(R.drawable.border_amount_bg)

        })
        findViewById<TextView>(R.id.profile_it).setOnClickListener(View.OnClickListener {  It = !It
            if(It)
                findViewById<TextView>(R.id.profile_it).setBackgroundResource(R.drawable.stroke_bg)
            else
                findViewById<TextView>(R.id.profile_it).setBackgroundResource(R.drawable.border_amount_bg)

        })
        findViewById<TextView>(R.id.profile_mining).setOnClickListener(View.OnClickListener { mining = !mining
            if(mining)
                findViewById<TextView>(R.id.profile_mining).setBackgroundResource(R.drawable.stroke_bg)
            else
                findViewById<TextView>(R.id.profile_mining).setBackgroundResource(R.drawable.border_amount_bg)
        })
        findViewById<TextView>(R.id.profile_mech).setOnClickListener(View.OnClickListener { mechanical = !mechanical
            if(mechanical)
                findViewById<TextView>(R.id.profile_mech).setBackgroundResource(R.drawable.stroke_bg)
            else
                findViewById<TextView>(R.id.profile_mech).setBackgroundResource(R.drawable.border_amount_bg)

        })
        findViewById<TextView>(R.id.profile_elec).setOnClickListener(View.OnClickListener { electrical = !electrical
            if(electrical)
                findViewById<TextView>(R.id.profile_elec).setBackgroundResource(R.drawable.stroke_bg)
            else
                findViewById<TextView>(R.id.profile_elec).setBackgroundResource(R.drawable.border_amount_bg)
        })
        findViewById<TextView>(R.id.profile_civil).setOnClickListener(View.OnClickListener { civil = !civil
            if(civil)
                findViewById<TextView>(R.id.profile_civil).setBackgroundResource(R.drawable.stroke_bg)
            else
                findViewById<TextView>(R.id.profile_civil).setBackgroundResource(R.drawable.border_amount_bg)
        })
        findViewById<TextView>(R.id.profile_ett).setOnClickListener(View.OnClickListener { etnt = !etnt
            if(etnt)
                findViewById<TextView>(R.id.profile_ett).setBackgroundResource(R.drawable.stroke_bg)
            else
                findViewById<TextView>(R.id.profile_ett).setBackgroundResource(R.drawable.border_amount_bg)
        })

    }

    fun observe_sem()
    {
        findViewById<TextView>(R.id.profile_one).setOnClickListener(View.OnClickListener { one_sem = !one_sem
            if(one_sem)
                findViewById<TextView>(R.id.profile_one).setBackgroundResource(R.drawable.stroke_bg)
            else
                findViewById<TextView>(R.id.profile_one).setBackgroundResource(R.drawable.border_amount_bg)
        })

        findViewById<TextView>(R.id.profile_two).setOnClickListener(View.OnClickListener { two_sem = !two_sem
            if(two_sem)
                findViewById<TextView>(R.id.profile_two).setBackgroundResource(R.drawable.stroke_bg)
            else
                findViewById<TextView>(R.id.profile_two).setBackgroundResource(R.drawable.border_amount_bg)
        })

        findViewById<TextView>(R.id.profile_three).setOnClickListener(View.OnClickListener { three_sem = !three_sem
            if(three_sem)
                findViewById<TextView>(R.id.profile_three).setBackgroundResource(R.drawable.stroke_bg)
            else
                findViewById<TextView>(R.id.profile_three).setBackgroundResource(R.drawable.border_amount_bg)
        })

        findViewById<TextView>(R.id.profile_four).setOnClickListener(View.OnClickListener { four_sem = !four_sem
            if(four_sem)
                findViewById<TextView>(R.id.profile_four).setBackgroundResource(R.drawable.stroke_bg)
            else
                findViewById<TextView>(R.id.profile_four).setBackgroundResource(R.drawable.border_amount_bg)
        })

        findViewById<TextView>(R.id.profile_five).setOnClickListener(View.OnClickListener { five_sem = !five_sem
            if(five_sem)
                findViewById<TextView>(R.id.profile_five).setBackgroundResource(R.drawable.stroke_bg)
            else
                findViewById<TextView>(R.id.profile_five).setBackgroundResource(R.drawable.border_amount_bg)
        })

        findViewById<TextView>(R.id.profile_six).setOnClickListener(View.OnClickListener { six_sem = !six_sem
            if(six_sem)
                findViewById<TextView>(R.id.profile_six).setBackgroundResource(R.drawable.stroke_bg)
            else
                findViewById<TextView>(R.id.profile_six).setBackgroundResource(R.drawable.border_amount_bg)
        })

        findViewById<TextView>(R.id.profile_seven).setOnClickListener(View.OnClickListener { seven_sem = !seven_sem
            if(seven_sem)
                findViewById<TextView>(R.id.profile_seven).setBackgroundResource(R.drawable.stroke_bg)
            else
                findViewById<TextView>(R.id.profile_seven).setBackgroundResource(R.drawable.border_amount_bg)})

        findViewById<TextView>(R.id.profile_eight).setOnClickListener(View.OnClickListener { eight_sem = !eight_sem
            if(eight_sem)
                findViewById<TextView>(R.id.profile_eight).setBackgroundResource(R.drawable.stroke_bg)
            else
                findViewById<TextView>(R.id.profile_eight).setBackgroundResource(R.drawable.border_amount_bg)
        })

    }
    fun validate():Boolean
    {
        return !(name=="" || email == "")
    }

}