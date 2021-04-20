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
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.ldnhat.adapter.PageAdapter
import com.ldnhat.adapter.TweetAdapter
import com.ldnhat.api.BaseConfig
import com.ldnhat.api.tweets.APITweet
import com.ldnhat.api.users.APIUsers
import com.ldnhat.fragment.HomeFragment
import com.ldnhat.fragment.MessageFragment
import com.ldnhat.fragment.NotificationFragment
import com.ldnhat.fragment.SearchFragment
import com.ldnhat.lostbird.R
import com.ldnhat.model.Tweet
import com.ldnhat.model.Users
import com.ldnhat.ui.login.PreloginActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar_layout.*
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
    private lateinit var recyclerView:RecyclerView
    private lateinit var tweetAdapter:TweetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //check login
        sharedPreferences = this.getSharedPreferences("user_information", Context.MODE_PRIVATE)
        if (sharedPreferences?.getString("userId", null) !== null){
            setDrawer()
            handleViewpager()
            getUser()
            tweet()
        }else{
            val intent = Intent(this@HomeActivity, PreloginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleViewpager(){
        fragments = ArrayList()
        viewPager = findViewById(R.id.viewpager)

        val homeFragment = HomeFragment()
        var messageFragment = MessageFragment()
        var notificationFragment = NotificationFragment()
        var searchFragment = SearchFragment()

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

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {


            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.position == 0){
                    tweet()
                }
            }

        })
    }

    // set drawer
    private fun setDrawer(){
        setSupportActionBar(toolbar)

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

    @SuppressLint("SetTextI18n")
    private fun handleUser(user: Users){
        val userImage:ImageView = nav_view.getHeaderView(0).findViewById(R.id.imageView)
        val username:TextView = nav_view.getHeaderView(0).findViewById(R.id.nav_username)
        val screenName:TextView = nav_view.getHeaderView(0).findViewById(R.id.txt_header_screen_name)
        val following:TextView = nav_view.getHeaderView(0).findViewById(R.id.txt_menu_following)
        val follower:TextView = nav_view.getHeaderView(0).findViewById(R.id.txt_menu_follower)

        username.text = user.username.toString()
        Picasso.with(this).load(user.profileImage).into(userImage)
        screenName.text = user.screenName.toString()
        following.text = user.following.toString() + " đang theo dõi"
        follower.text = user.follower.toString() + " người theo dõi"


    }

    private fun tweet(){

        val apiTweets = BaseConfig().getInstance().create(APITweet::class.java)
        val userId = sharedPreferences?.getString("userId", null)
        if (userId != null) {
            apiTweets.tweet(userId.toInt(), "RESPONSE_TWEET_BY_USER_ID").enqueue(object : Callback<MutableList<Tweet>>{
                override fun onResponse(
                    call: Call<MutableList<Tweet>>,
                    response: Response<MutableList<Tweet>>
                ) {
                    var tweets:MutableList<Tweet>? = response.body()
                    if (tweets != null) {
                        handleTweet(tweets)
                    }
                }

                override fun onFailure(call: Call<MutableList<Tweet>>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }
    }


    private fun handleTweet(tweets:MutableList<Tweet>){
        recyclerView = findViewById(R.id.rv_tweet)
        tweetAdapter = TweetAdapter(this, tweets)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = tweetAdapter

        tweetAdapter.notifyDataSetChanged()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                sharedPreferences?.edit()?.clear()?.apply()
                startActivity(Intent(this, PreloginActivity::class.java))
            }
        }
        return true
    }
}
