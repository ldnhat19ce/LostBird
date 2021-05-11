package com.ldnhat.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ldnhat.adapter.ITweetAdapter
import com.ldnhat.adapter.TweetAdapter
import com.ldnhat.api.BaseConfig
import com.ldnhat.api.likes.APILike
import com.ldnhat.api.tweets.APITweet
import com.ldnhat.lostbird.R
import com.ldnhat.model.Tweet
import com.ldnhat.model.Users
import com.ldnhat.ui.tweet.DetailTweetActivity
import maes.tech.intentanim.CustomIntent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), ITweetAdapter{

    private var sharedPreferences: SharedPreferences? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var tweetAdapter: TweetAdapter
    private lateinit var swipeRefreshLayout:SwipeRefreshLayout
    private lateinit var rootView:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = context?.getSharedPreferences("user_information", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_home, container, false)

        swipeRefreshLayout = rootView.findViewById(R.id.sw_refresh_home_tweet)

        tweet(rootView)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val fragmentManager: FragmentManager? = parentFragmentManager


        swipeRefreshLayout.setOnRefreshListener {
            tweet(rootView)
            updateTweet()
        }
    }

    private fun updateTweet(){


        if(this.isAdded){ //Return true if the fragment is currently added to its activity.
            swipeRefreshLayout.isRefreshing = true

           //tweetAdapter.notifyDataSetChanged()

            if(swipeRefreshLayout.isRefreshing){
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun tweet(v:View){

        val apiTweets = BaseConfig().getInstance().create(APITweet::class.java)
        val userId = sharedPreferences?.getString("userId", null)
        if (userId != null) {
            apiTweets.tweet(userId.toInt(), "RESPONSE_TWEET_BY_USER_ID").enqueue(object :
                Callback<MutableList<Tweet>> {
                override fun onResponse(
                    call: Call<MutableList<Tweet>>,
                    response: Response<MutableList<Tweet>>
                ) {
                    var tweets:MutableList<Tweet>? = response.body()
                    if (tweets != null) {
                        handleTweet(tweets, v)

                    }
                }

                override fun onFailure(call: Call<MutableList<Tweet>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }


    private fun handleTweet(tweets:MutableList<Tweet>, v:View){
        recyclerView = v.findViewById(R.id.rv_tweet)
        tweetAdapter = context?.let { TweetAdapter(it, tweets, this) }!!
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = tweetAdapter

        tweetAdapter.notifyDataSetChanged()
    }

    override fun checkLike(isLike : Int, tweet: Tweet, itemStateArray: SparseBooleanArray, position: Int) {
        val apiLike = BaseConfig().getInstance().create(APILike::class.java)
        if(!itemStateArray.get(position, false)){
           // Toast.makeText(context, "like", Toast.LENGTH_SHORT).show()
            itemStateArray.put(position, true)
            tweet.user?.id?.let {
                apiLike.saveByUserIdAndTweetId("SAVE_LIKE_BY_USERID_AND_TWEETID",
                    it, tweet.id).enqueue(object : Callback<Void>{
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        t.printStackTrace()
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {

                    }

                })
            }
            //tweet(rootView)
        }else{
           // Toast.makeText(context, "unlike", Toast.LENGTH_SHORT).show()
            itemStateArray.put(position, false)
            apiLike.deleteLike("DELETE_LIKE_BY_TWEET_ID", tweet.id).enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {

                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    t.printStackTrace()
                }

            })
            //tweet(rootView)
        }
    }

    override fun ItemClicklistener(tweet: Tweet, position: Int) {
        val intent = Intent(context, DetailTweetActivity::class.java)


        Log.d("user id pre", tweet.user?.id.toString())
        intent.putExtra("TWEET_OBJECT", tweet)
        intent.putExtra("TWEET_POSITION", position)

        startActivityForResult(intent, 51)
        CustomIntent.customType(context, "left-to-right")
    }
}