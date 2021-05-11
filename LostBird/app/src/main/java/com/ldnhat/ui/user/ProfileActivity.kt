package com.ldnhat.ui.user

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ldnhat.api.BaseConfig
import com.ldnhat.api.users.APIUsers
import com.ldnhat.lostbird.R
import com.ldnhat.model.Users
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.profile_acitivity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class ProfileActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    private val apiUsers:APIUsers = BaseConfig().getInstance().create(APIUsers::class.java)
    private var user:Users? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_acitivity)

        setSupportActionBar(toolbar_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPreferences = this.getSharedPreferences("user_information", Context.MODE_PRIVATE)

        getDataUser()

        btn_setting_profile.setOnClickListener {
            val intent = Intent(this, SettingUserActivity::class.java)
            intent.putExtra("USER", user)
            startActivityForResult(intent, 1)
        }
    }

    private fun getDataUser(){
        val intent = intent

        if (intent.getParcelableExtra<Users>("USER") == null){
            val userId:Int = sharedPreferences?.getString("userId", null)!!.toInt()

            apiUsers.findOne(userId, "FIND_ONE_USER").enqueue(object : Callback<Users>{
                override fun onResponse(call: Call<Users>, response: Response<Users>) {
                    user = response.body()
                    handleUser(user!!)
                }

                override fun onFailure(call: Call<Users>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }else{
            user = intent.getParcelableExtra("USER")
            btn_setting_profile.visibility = View.GONE
            handleUser(user!!)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleUser(user:Users){
        Picasso.get().load(user.profileCover).into(backdrop)
        Picasso.get().load(user.profileImage).into(profile_image_user)

        profile_user_screenname.text = user.screenName
        profile_user_username.text = "@"+user.username

        val dateString = SimpleDateFormat("MM/dd/yyyy").format(user.createDate)
        profile_create_date.text = "Đã tham gia vào "+dateString

        profile_following.text = user.following.toString() + " Đang theo dõi"
        profile_follower.text = user.follower.toString() + " Người theo dõi"
    }
}