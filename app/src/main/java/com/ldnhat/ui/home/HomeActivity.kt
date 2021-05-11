package com.ldnhat.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.ldnhat.adapter.PageAdapter
import com.ldnhat.api.BaseConfig
import com.ldnhat.api.users.APIUsers
import com.ldnhat.fragment.*
import com.ldnhat.lostbird.R
import com.ldnhat.model.Users
import com.ldnhat.ui.login.PreloginActivity
import com.ldnhat.ui.tweet.SaveTweetActivity
import com.ldnhat.ui.user.ProfileActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import maes.tech.intentanim.CustomIntent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var sharedPreferences: SharedPreferences? = null

    private lateinit var navigationView: NavigationView
    private var viewPager: ViewPager? = null
    lateinit var pageAdaper:PageAdapter
    lateinit var fragments:MutableList<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        //check login
        sharedPreferences = this.getSharedPreferences("user_information", Context.MODE_PRIVATE)
        if (sharedPreferences?.getString("userId", null) !== null){
            setDrawer()
            handleViewpager()
            getUser()
        }else{
            val intent = Intent(this@HomeActivity, PreloginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleViewpager(){
        fragments = ArrayList()
        viewPager = findViewById(R.id.viewpager)

        val homeFragment = HomeFragment()
        val messageFragment = MessageFragment()
        val notificationFragment = NotificationFragment()
        val searchFragment = SearchFragment()

        fragments.add(homeFragment)
        fragments.add(searchFragment)
        fragments.add(notificationFragment)
        fragments.add(messageFragment)

        pageAdaper = PageAdapter(supportFragmentManager, 1, fragments)
        viewPager?.adapter = pageAdaper
        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)?.setIcon(R.drawable.baseline_home_black_18dp)
        tabs.getTabAt(1)?.setIcon(R.drawable.baseline_search_black_18dp)
        tabs.getTabAt(2)?.setIcon(R.drawable.baseline_notifications_none_24)
        tabs.getTabAt(3)?.setIcon(R.drawable.baseline_mark_email_unread_black_18dp)

        fab_add_tweet.setOnClickListener {
            val intent = Intent(this, SaveTweetActivity::class.java)
            intent.putExtra("TAB_POSITION", tabs.selectedTabPosition)
            startActivityForResult(intent, 10)
            CustomIntent.customType(this, "bottom-to-up")
        }
   }

    // set drawer
    private fun setDrawer(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun getUser(){
        val apiUsers = BaseConfig().getInstance().create(APIUsers::class.java)
        val userId = sharedPreferences?.getString("userId", null)
        if (userId != null) {
            apiUsers.findOne(userId.toInt(), "FIND_ONE_USER").enqueue(object : Callback<Users> {
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

    @SuppressLint("SetTextI18n")
    private fun handleUser(user: Users){
        val userImage:ImageView = nav_view.getHeaderView(0).findViewById(R.id.imageView)
        val username:TextView = nav_view.getHeaderView(0).findViewById(R.id.nav_username)
        val screenName:TextView = nav_view.getHeaderView(0).findViewById(R.id.txt_header_screen_name)
        val following:TextView = nav_view.getHeaderView(0).findViewById(R.id.txt_menu_following)
        val follower:TextView = nav_view.getHeaderView(0).findViewById(R.id.txt_menu_follower)

        username.text = user.username.toString()
        Picasso.get().load(user.profileImage).into(userImage)
        screenName.text = user.screenName.toString()
        following.text = user.following.toString() + " đang theo dõi"
        follower.text = user.follower.toString() + " người theo dõi"


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                sharedPreferences?.edit()?.clear()?.apply()
                startActivity(Intent(this, PreloginActivity::class.java))
            }

            R.id.nav_profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10){
            val position: Int? = data?.getIntExtra("TAB_POSITION", 0)
            Log.d("position", position.toString())
            if (position != null) {
                tabs.setScrollPosition(position, 0f, true)
                viewPager?.currentItem = position
            }
        }
    }



}
