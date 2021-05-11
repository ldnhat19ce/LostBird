package com.ldnhat.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ldnhat.api.BaseConfig
import com.ldnhat.api.users.APIUsers
import com.ldnhat.lostbird.R
import com.ldnhat.model.Role
import com.ldnhat.model.Users
import com.ldnhat.ui.home.HomeActivity
import com.ldnhat.utils.RegisterPattern
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.register_activity.*
import kotlinx.android.synthetic.main.toolbar_register.*
import kotlinx.android.synthetic.main.toolbar_register.toolbar_register
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp

class RegisterActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        setSupportActionBar(toolbar_register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        //* is also used to pass an array to a vararg parameter
        ed_register_name.filters = arrayOf(*ed_register_name.filters, InputFilter.LengthFilter(20))

        btn_register.setOnClickListener {
            val user = Users()
            if(validateEmail(user) && validateUsername(user) && validatePassword(user)){
                user.screenName = ed_register_screen_name.text.toString()
                user.message = "REGISTER"
                val role = Role()
                role.id = 2
                user.role = role
                user.profileImage = "http:/192.168.1.6:8080/DemoJson_war_exploded/image_user/sign.png"
                user.profileCover = "http:/192.168.1.6:8080/DemoJson_war_exploded/image_user/profile_cover.jpg"
                user.following = 0
                user.follower = 0
                user.bio = ""

                val apiUsers = BaseConfig().getInstance().create(APIUsers::class.java)
                apiUsers.save(user).enqueue(object : Callback<Users>{
                    override fun onResponse(call: Call<Users>, response: Response<Users>) {
                        val userResponse:Users? = response.body()

                        if(userResponse?.message.equals("REGISTER_ACCOUNT_SUCCESS")){
                            val saveAccountInformation:SharedPreferences =
                                this@RegisterActivity.getSharedPreferences("user_information", Context.MODE_PRIVATE)
                            val editor:SharedPreferences.Editor = saveAccountInformation.edit()
                            editor.putString("userId", userResponse?.id.toString())
                            editor.putString("roleId", userResponse?.role?.id.toString())
                            editor.putString("profileImage", userResponse?.profileImage)
                            editor.apply()

                            Toast.makeText(this@RegisterActivity, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
                        }else if (userResponse?.message.equals("REGISTER_NAME_OR_EMAIL_EXIS")){
                            Toast.makeText(this@RegisterActivity, "tài khoản hoặc email đã được sử dụng", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<Users>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
            }
        }
    }

    private fun validateEmail(user: Users) : Boolean{
        var check = false
        //check email
        if(!RegisterPattern().registerValidation(ed_register_email.text.toString(), RegisterPattern().VALID_EMAIL_ADDRESS_REGEX)){
            txt_email_register_error.visibility = View.VISIBLE
            txt_email_register_error.text = "email không hợp lệ"
            check = false
        }else{
            txt_email_register_error.visibility = View.INVISIBLE
            user.email = ed_register_email.text.toString()
            check = true
        }
        return check
    }

    private fun validateUsername(user: Users) : Boolean{
        var check = false
        //check username
        if(!RegisterPattern().registerValidation(ed_register_name.text.toString(), RegisterPattern().VALID_USERNAME_REGEX)){
            txt_count_name.setTextColor(ContextCompat.getColor(this, R.color.red_error))
            txt_count_name.text = "chỉ kí tự hoặc số"
            check = false
        }else{
            user.username = ed_register_name.text.toString()
            check = true
        }
        return check
    }

    private fun validatePassword(user: Users) : Boolean{
        var check = false
        //check password
        if(!RegisterPattern().registerValidation(ed_register_password.text.toString(), RegisterPattern().VALID_PASSWORD_REGEX)){
            txt_password_register_error.visibility = View.VISIBLE
            txt_password_register_error.text = "từ 8 đến 20 kí tự"
            check = false
        }else{
            user.password = ed_register_password.text.toString()
            txt_password_register_error.visibility = View.VISIBLE
            check = true
        }
        return check
    }
}
