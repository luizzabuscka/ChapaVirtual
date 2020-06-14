package com.ccr.hackaton.chapavirtual

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.startActivity

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btSave.setOnClickListener {
            startActivity<HomeActivity>()
            finish()
        }
    }
}
