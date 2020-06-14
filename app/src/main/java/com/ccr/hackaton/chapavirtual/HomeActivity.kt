package com.ccr.hackaton.chapavirtual

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.*
import java.util.*
import kotlin.concurrent.schedule

class HomeActivity : AppCompatActivity() {

    private val progressDialog by lazy {
        indeterminateProgressDialog("Carregando...")
    }

    private val alertDanger by lazy {
        alert("Deseja realmente enviar um alerta de roubo?") {
            yesButton {
                sendData()
            }
            noButton {  }
        }
    }

    private val alertSick by lazy {
        alert{
            customView = layoutInflater.inflate(R.layout.dialog_sick, null)
            okButton {
                sendData()
            }
        }
    }

    private val alertMecanico by lazy {
        alert{
            customView = layoutInflater.inflate(R.layout.dialog_mecanico, null)
            okButton {
                sendData()
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        ivDanger.setOnClickListener {
            alertDanger.show()
        }

        ivSick.setOnClickListener {
            alertSick.show()
        }

        ivMechanic.setOnClickListener {
            alertMecanico.show()
        }

        ivStop.setOnClickListener {
            startActivity<MapsActivity>()
        }


    }

    private fun sendData() {
        progressDialog.show()
        Timer("carregando", false).schedule(1000) {
            runOnUiThread {
                progressDialog.dismiss()
                toast("Enviado com sucesso!")
            }
        }

    }
}
