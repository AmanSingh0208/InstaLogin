package com.example.userlogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

const val RC_SIGN_IN = 123

class MainActivity : AppCompatActivity() {
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
            val email: String = userLoginEmail.text.toString()
            val pass: String = userLoginPassword.text.toString()

            if (email == ValidateUser.email && pass == ValidateUser.password) {
                val intent = Intent(applicationContext, Homepage::class.java)
                intent.putExtra("name", ValidateUser.name)
                intent.putExtra("img",ValidateUser.img)
                startActivity(intent)
            } else {
                showToast("Incorrect Email/Password")
            }
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