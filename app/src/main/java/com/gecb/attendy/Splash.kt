package com.gecb.attendy

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import soup.neumorphism.NeumorphButton

class Splash : AppCompatActivity() {
    var onboard: View? =null
    private var auth: FirebaseAuth? = null
    var dialog: Dialog? = null
    var downspeed = 0
    var upspeed = 0
    var agooglesigninclient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 101
    private var mAuth: FirebaseAuth? = null
    var user: FirebaseUser? = null
    var user_reference: DatabaseReference? = null
    var DeviceToken: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor =
            ContextCompat.getColor(this, R.color.green_300)
        window.navigationBarColor =
            ContextCompat.getColor(this, R.color.green_300)

        auth = FirebaseAuth.getInstance()
        user = auth!!.getCurrentUser()
        user_reference = FirebaseDatabase.getInstance().reference.child("users")
        findViewById<View>(R.id.login).findViewById<LinearLayout>(R.id.imageView).setOnClickListener(View.OnClickListener { view: View? -> signIn_Google() })

        findViewById<View>(R.id.onboard).visibility=View.VISIBLE
        findViewById<View>(R.id.login).visibility=View.GONE

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        agooglesigninclient = GoogleSignIn.getClient(this, gso)

        val button=findViewById<NeumorphButton>(R.id.proceed)
        button.setOnClickListener{
            findViewById<View>(R.id.onboard).visibility=View.GONE
            findViewById<View>(R.id.login).visibility=View.VISIBLE
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor =
                ContextCompat.getColor(this, R.color.dark_blue)
            window.navigationBarColor =
                ContextCompat.getColor(this, R.color.dark_blue)
        }

    }
    private fun signIn_Google() {
        val SignInIntent = agooglesigninclient!!.signInIntent
        startActivityForResult(SignInIntent, RC_SIGN_IN)
        dialog = Dialog(this@Splash)
        dialog!!.setCancelable(true)
        dialog!!.setContentView(R.layout.loading_dialog)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.show()
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(this, "Something went wrong. "+e, Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth = FirebaseAuth.getInstance()
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(
                this
            ) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    dialog!!.dismiss()
                    user = mAuth!!.getCurrentUser()
                    assert(user != null)

                    Toast.makeText(this@Splash,"Login Successfully!!.",Toast.LENGTH_LONG).show()

                    user_reference!!.child(
                        user!!.uid
                    ).child("email").setValue(
                        user!!.email
                    )
                    user_reference!!.child(user!!.uid).child("uid").setValue(user!!.uid)
                    user_reference!!.child(user!!.uid).child("name").setValue(user!!.displayName)
                    user_reference!!.child(user!!.uid).child("token").setValue(DeviceToken)
                    user_reference!!.child(user!!.uid).child("role").setValue("student")
                    updateUI()
                } else {
                    Toast.makeText(this@Splash,"Login Failed!!.",Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun updateUI() {
        val intent = Intent(this@Splash, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        val user = auth!!.currentUser
        if (user != null) {
            updateUI()
        }
    }
}