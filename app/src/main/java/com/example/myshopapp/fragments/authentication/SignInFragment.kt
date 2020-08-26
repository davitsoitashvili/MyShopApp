package com.example.myshopapp.fragments.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.myshopapp.EMAIL
import com.example.myshopapp.R
import com.example.myshopapp.activities.MyShopDashBoardActivity
import com.example.myshopapp.fragments.BaseFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_in.view.*

class SignInFragment : BaseFragment() {

    private var mAuth: FirebaseAuth? = null

    override fun getFragmentLayout() = R.layout.fragment_sign_in

    override fun startFragmentConfiguration(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        init()
    }

    private fun init() {
        mAuth = FirebaseAuth.getInstance()

        itemView!!.signInButton.setOnClickListener {
            checkFields()
        }
        itemView!!.signUpButton.setOnClickListener {
            openSignUpFragment()
        }
    }

    private fun openSignUpFragment() {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.authenticationContainer,
            SignUpFragment()
        )
        transaction.commit()
    }

    private fun checkFields() {
        when {
            itemView!!.emailInputField.text.isEmpty() -> {
                itemView!!.emailInputField.error = "Fill this field"
            }
            itemView!!.passwordInputField.text.isEmpty() -> {
                itemView!!.passwordInputField.error = "Fill this field"
            }
            !Patterns.EMAIL_ADDRESS.matcher(itemView!!.emailInputField.text).matches() -> {
                itemView!!.emailInputField.error = "Type valid email address"
            }
            else -> {
                signIn(
                    itemView!!.emailInputField.text.toString(),
                    itemView!!.passwordInputField.text.toString()
                )

            }
        }
    }

    private fun signIn(email: String, password: String) {
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity(),
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        if (mAuth!!.currentUser!!.isEmailVerified) {
                            openDashBoardActivity()
                        } else {
                            Toast.makeText(activity, "Please verify your email",Toast.LENGTH_LONG).show()
                        }

                    } else {
                        Toast.makeText(
                            activity,
                            "email or password are not correct!",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                })
    }

    private fun openDashBoardActivity() {
        val intent = Intent(activity, MyShopDashBoardActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(EMAIL, mAuth!!.currentUser!!.email)
        startActivity(intent)
    }

}
