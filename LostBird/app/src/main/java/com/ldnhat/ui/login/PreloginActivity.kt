package com.ldnhat.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ldnhat.lostbird.R
import kotlinx.android.synthetic.main.prelogin_activity.*

class PreloginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prelogin_activity)

        txt_login.setOnClickListener(this)
        btn_create_account.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent: Intent
        when(v?.id){
            R.id.btn_create_account -> {
                intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }

            R.id.txt_login -> {
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}