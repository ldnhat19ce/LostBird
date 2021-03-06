package com.ldnhat.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.ldnhat.api.BaseConfig
import com.ldnhat.api.tweets.APITweet
import com.ldnhat.api.users.APIUsers
import com.ldnhat.lostbird.R
import com.ldnhat.model.Users
import com.ldnhat.ui.home.HomeActivity
import com.ldnhat.utils.BaseUrl
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.toolbar_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        setSupportActionBar(toolbar_register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        btn_login.setOnClickListener {
            val apiUsers = BaseConfig().getInstance().create(APIUsers::class.java)

            var user = Users()
            user.email = ed_enter_email.text.toString()
            user.password = ed_enter_password.text.toString()
            user.message = "CLIENT_REQUIRED_AUTHORIZATION"

            apiUsers.login(user).enqueue(object : Callback<Users> {
                override fun onResponse(call: Call<Users>, response: Response<Users>) {
                    val userResponse = response.body()
                    if(userResponse?.message.equals("AUTHORIZATION_SUCCESSFUL")){
                        val saveAccountInformation: SharedPreferences =
                            this@LoginActivity.getSharedPreferences("user_information", Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = saveAccountInformation.edit()
                        editor.putString("userId", userResponse?.id.toString())
                        editor.putString("roleId", userResponse?.role?.id.toString())
                        editor.putString("profileImage", userResponse?.profileImage)
                        editor.apply()

                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@LoginActivity, "email ho???c m???t kh???u kh??ng ch??nh x??c", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Users>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }
    }

}