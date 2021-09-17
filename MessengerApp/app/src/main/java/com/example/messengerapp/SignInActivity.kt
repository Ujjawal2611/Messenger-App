package com.example.messengerapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.annotation.NonNull
import com.example.messengerapp.databinding.ActivitySignInBinding

import com.google.android.gms.tasks.OnCompleteListener





class SignInActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignInBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        binding.tvCreateAnAccount.setOnClickListener(this)
        binding.GoogleSignIn.setOnClickListener(this)
        binding.btnSignIn.setOnClickListener(this)
        auth= Firebase.auth

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

            .requestEmail()
            .build()


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);




    }

    private fun update(account: GoogleSignInAccount?) {
        if(account==null){
            //signIn()
        }
        else{
            val personName: String? = account.displayName
            val personGivenName: String? = account.givenName
            val personFamilyName: String? = account.familyName
            val personEmail: String? = account.email
            val personId: String? = account.id
            Log.e("person name","${personFamilyName}")
        }

    }
    var RC_SIGN_IN=50
    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            //if(mGoogleSignInClient.isConnected())
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("tag", "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }


    private fun updateUI(account: GoogleSignInAccount?) {

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvCreateAnAccount->{
                val intent=Intent(this,SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.Google_SignIn->{

                val account = GoogleSignIn.getLastSignedInAccount(this);
                update(account)
                signIn()
            }
            R.id.btnSignIn->{

                signOut()
                signEmail()
            }
        }
    }
    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                // ...
            }
    }

    private fun signEmail() {
        val email: String =binding.tietEmails.text.toString()
        val password: String =binding.tietPasswords.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.e("tag", "signInWithEmail:success")
                    val user = auth.currentUser
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e("tiet", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }
}