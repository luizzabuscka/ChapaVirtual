package com.ccr.hackaton.chapavirtual

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {


    private val progressValidating by lazy {indeterminateProgressDialog("Validando número de celular...")}
    private val progressLogin by lazy { indeterminateProgressDialog("Fazendo login...")}

    private val dialogFail by lazy {
        alert("Falha ao validar telefone, por favor tente novamente.") {
            okButton {  }
        }
    }

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            progressValidating.dismiss()
            progressLogin.show()
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            progressValidating.dismiss()
            progressLogin.dismiss()
            dialogFail.show()
        }

        override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
        ) {}
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btLogin.setOnClickListener {
            progressValidating.show()
//            PhoneAuthProvider.getInstance().verifyPhoneNumber(etPhone.text.toString(), 60, TimeUnit.SECONDS, this, callbacks)

            startActivity<SignUpActivity>("phone" to etPhone.text.toString())
            finish()
        }

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    progressLogin.dismiss()
                    if (task.isSuccessful) {
                        val user = task.result?.user
                        startActivity<SignUpActivity>("phone" to etPhone.text.toString())
                        finish()
                    } else {
                        dialogFail.show()
                    }
                }
    }
}
