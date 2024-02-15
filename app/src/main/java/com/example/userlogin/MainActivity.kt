package com.example.userlogin

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.identity.SignInPassword
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

const val RC_SIGN_IN = 123

class MainActivity : AppCompatActivity() {

    companion object {
        const val KEY = "com.example.userlogin.MainActivity.KEY"
    }

    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userRegistration = findViewById<TextView>(R.id.register)
        val userLoginEmail = findViewById<EditText>(R.id.login_user_email)
        val userLoginPassword = findViewById<EditText>(R.id.login_user_password)
        val loginButton = findViewById<Button>(R.id.login_button)
        val btnGoogle = findViewById<Button>(R.id.btnGoogle)
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        val gsc = GoogleSignIn.getClient(this, gso)



        loginButton.setOnClickListener {
            val mobileNum: String = userLoginEmail.text.toString()
            val pass: String = userLoginPassword.text.toString()

            if (mobileNum.isNotEmpty() && pass.isNotEmpty()) readData(mobileNum, pass)
            else showToast("Please input Email/Password")
        }

        btnGoogle.setOnClickListener() {
            val signInIntent: Intent = gsc.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)

        }

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName = acct.displayName
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
            val personEmail = acct.email
            val personId = acct.id
            val personPhoto = acct.photoUrl
        }



        userRegistration.setOnClickListener {
            val intent = Intent(this, UserRegistration::class.java)
            startActivity(intent)
        }
    }

    //User Authentication
    private fun readData(mobileNum: String, password: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.child(mobileNum).get().addOnSuccessListener {

            if (it.exists()) {

                val number = it.child("mobileNum").value
                val passwordUser = it.child("password").value
                val userName = it.child("name").value.toString()

                if (number == mobileNum && passwordUser == password) {
                    val intentNew = Intent(this, Homepage::class.java)
                    intentNew.putExtra(KEY, userName)
                    startActivity(intentNew)
                } else {
                    showToast("Invalid Email/Password")
                }
            } else {
                showToast("You're not registered, Please Register..")
            }
        }.addOnFailureListener {
            showToast("Server not responding..")
        }


    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.

            val intent = Intent(applicationContext, Homepage::class.java)
            intent.putExtra("name", account.displayName)
            startActivity(intent)

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            showToast("Please sign-in first")
        }
    }

}