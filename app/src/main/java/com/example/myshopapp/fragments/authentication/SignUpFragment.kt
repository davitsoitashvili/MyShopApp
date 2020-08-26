package com.example.myshopapp.fragments.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.myshopapp.EMAIL
import com.example.myshopapp.R
import com.example.myshopapp.activities.MyShopDashBoardActivity
import com.example.myshopapp.fragments.BaseFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.emailField


class SignUpFragment : BaseFragment() {
    private var mAuth: FirebaseAuth? = null

    override fun getFragmentLayout() = R.layout.fragment_sign_up

    override fun startFragmentConfiguration(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        init()
    }


    private fun init() {
        mAuth = FirebaseAuth.getInstance()
        itemView!!.signInBtn.setOnClickListener() {
            openSignInFragment()
        }

        itemView!!.signUpBtn.setOnClickListener {
            checkFields()
        }
        checkUser()

    }

    private fun checkUser() {
        if (mAuth!!.currentUser != null) {
            openDashBoardActivity()
        }
    }

    private fun openSignInFragment() {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.authenticationContainer,
            SignInFragment(), "SignInFragment"
        )
        transaction.addToBackStack("SignInFragment")
        transaction.commit()
    }

    private fun checkFields() {
        when {
            itemView!!.firstNameField.text.isEmpty() -> {
                itemView!!.firstNameField.error = "Fill this field"
            }
            itemView!!.lastNameField.text.isEmpty() -> {
                itemView!!.lastNameField.error = "Fill this field"
            }
            itemView!!.emailField.text.isEmpty() -> {
                itemView!!.emailField.error = "Fill this field"
            }
            itemView!!.passwordField.text.isEmpty() -> {
                itemView!!.passwordField.error = "Fill this field"
            }
            itemView!!.confirmPasswordField.text.isEmpty() -> {
                itemView!!.confirmPasswordField.error = "Fill this field"
            }
            !Patterns.EMAIL_ADDRESS.matcher(itemView!!.emailField.text).matches() -> {
                itemView!!.emailField.error = "Type valid email address"
            }
            itemView!!.passwordField.text.toString() != itemView!!.confirmPasswordField.text.toString() -> {
                Toast.makeText(activity, "Passwords don't match!", Toast.LENGTH_LONG).show()
            }
            else -> {
                createNewUser(itemView!!.emailField.text.toString(), passwordField.text.toString())
            }
        }
    }

    private fun createNewUser(email: String, password: String) {
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity(),
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        val user = mAuth!!.currentUser
                        if (user != null) {
                            sendEmailVerification(user)
                            openSignInFragment()
                        }

                    } else {
                        Toast.makeText(activity, "Authentication failed", Toast.LENGTH_LONG).show()

                    }
                })
    }

    private fun openDashBoardActivity() {
        val intent = Intent(activity, MyShopDashBoardActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(EMAIL, mAuth!!.currentUser!!.email)
        startActivity(intent)
    }

    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(activity, "Verification sent to your email", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(activity, task.exception!!.message, Toast.LENGTH_LONG).show()
                }
            }
    }

}



