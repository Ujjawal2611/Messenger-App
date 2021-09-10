package com.example.messengerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.messengerapp.databinding.ActivitySignUpBinding

import com.example.messengerapp.models.User

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=Firebase.auth
        binding.btnSignUp.setOnClickListener(this)
        binding.tvAlreadyHaveAnAccount.setOnClickListener(this)
        val currentUser = auth.currentUser
       database= FirebaseDatabase.getInstance("https://messenger-app-6bebe-default-rtdb.asia-southeast1.firebasedatabase.app/")
       // database= FirebaseDatabase.getInstance()
        Log.e("datav","$database grtgrttrg")




    }



override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSignUp->{
                signUp()
            }
            R.id.tvAlreadyHaveAnAccount->{
                alreadyHaveAccount()
            }
        }
    }






    private fun alreadyHaveAccount() {
        val intent= Intent(this,SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun signUp() {

        val email=binding.tietEmail.text.toString()
        val password=binding.tietPassword.text.toString()
        val name=binding.tietName.text.toString()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,"Successful",Toast.LENGTH_LONG).show()
                   val users= User(name,email,password)
                    val id: String = task.result.user!!.uid
                    val myRef = database.getReference("Users").child(id)
                    myRef.setValue(users)
                    myRef.addValueEventListener(object: ValueEventListener {

                        override fun onDataChange(snapshot: DataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                          //  val value = snapshot.getValue<String>()
                            //Log.e("gggfg", "Value is: $value")
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.e("ggg", "Failed to read value.", error.toException())
                        }

                    })
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("tags", "createUserWithEmail:success")
                    val currentUser = auth.currentUser

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("tags", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }

    }



}

