package com.example.myshopapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myshopapp.R
import com.example.myshopapp.fragments.authentication.SignUpFragment

class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        openSignUpFragment()
    }

    private fun openSignUpFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.authenticationContainer,
            SignUpFragment(), "SignUpFragment")
        transaction.commit()
    }
}
