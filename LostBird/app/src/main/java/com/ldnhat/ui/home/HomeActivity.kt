package com.ldnhat.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.ldnhat.api.BaseConfig
import com.ldnhat.api.users.APIUsers
import com.ldnhat.lostbird.R
import com.ldnhat.model.Users
import com.ldnhat.ui.login.PreloginActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)

        //fragment quản lí
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home), drawer_layout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)

        //check login
        sharedPreferences = this.getSharedPreferences("user_information", Context.MODE_PRIVATE)
        if (sharedPreferences?.getString("userId", null) !== null){
            getUser()
        }else{
            val intent = Intent(this@HomeActivity, PreloginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()


    }

    private fun getUser(){
        val apiUsers = BaseConfig().getInstance().create(APIUsers::class.java)
        val userId = sharedPreferences?.getString("userId", null)
        if (userId != null) {
            apiUsers.findOne(userId.toInt(), "FIND_ONE_USER").enqueue(object : Callback<Users>{
                override fun onResponse(call: Call<Users>, response: Response<Users>) {
                    val userResponse = response.body()
                    if (userResponse != null) {
                        handleUser(userResponse)
                    }
                }

                override fun onFailure(call: Call<Users>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }
    }

    private fun handleUser(user: Users){
        val userImage:ImageView = nav_view.getHeaderView(0).findViewById(R.id.imageView)
        val username:TextView = nav_view.getHeaderView(0).findViewById(R.id.nav_username)
        Log.d("image", user.email.toString())
        username.text = user.username.toString()
        Picasso.with(this).load(user.profileImage).into(userImage)

    }
}