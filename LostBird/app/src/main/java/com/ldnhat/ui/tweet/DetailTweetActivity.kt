package com.ldnhat.ui.tweet

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ldnhat.adapter.CommentAdapter
import com.ldnhat.adapter.ICommentAdapter
import com.ldnhat.api.BaseConfig
import com.ldnhat.api.comments.APIComment
import com.ldnhat.lostbird.R
import com.ldnhat.model.Comment
import com.ldnhat.model.Tweet
import com.ldnhat.model.Users
import com.ldnhat.utils.BaseUrl
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_tweet_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class DetailTweetActivity : AppCompatActivity(), ICommentAdapter {

    private lateinit var recyclerView: RecyclerView
    private lateinit var commentAdapter: CommentAdapter
    private val apiComment = BaseConfig().getInstance().create(APIComment::class.java)
    private var sharedPreferences: SharedPreferences? = null
    private var comments:MutableList<Comment>? = null
    private lateinit var tweet: Tweet


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_tweet_activity)

        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPreferences = this.getSharedPreferences("user_information", Context.MODE_PRIVATE)
        getData()

    }

    private fun handleComment(comments:MutableList<Comment>){
        recyclerView = findViewById(R.id.rv_comment)
        commentAdapter = CommentAdapter(this, comments, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = commentAdapter
        commentAdapter.notifyDataSetChanged()
    }

    private fun getDataComment(tweet: Tweet){

        apiComment.findByTweetId(tweet.id, "FIND_COMMENT_BY_TWEET_ID").enqueue(object : Callback<MutableList<Comment>>{
            override fun onResponse(
                call: Call<MutableList<Comment>>,
                response: Response<MutableList<Comment>>
            ) {
                //val comments:MutableList<Comment> = response.body()!!
                comments = response.body()
                comments?.let { handleComment(it) }
            }

            override fun onFailure(call: Call<MutableList<Comment>>, t: Throwable) {
               t.printStackTrace()
            }

        })

    }

    private fun getData(){
        val intent = intent

       // val bundle = intent.extras
        val position = intent.getIntExtra("TWEET_POSITION", 0)
        Log.d("TWEET_POSITION", position.toString())
        tweet = intent.getParcelableExtra<Tweet>("TWEET_OBJECT") as Tweet

        Log.d("user id after", tweet.user?.id.toString())
        Picasso.get().load(tweet.user?.profileImage).into(tweet_detail_profile_image)
        tweet_detail_username.text = "@"+tweet.user?.username
        tweet_detail_screenname.text = tweet.user?.screenName

        tweet_detail_status.text = tweet.tweetStatus

        if(!tweet.tweetImage.equals("") || tweet.tweetImage != null){
            tweet_detail_card_view.visibility = View.VISIBLE
            Picasso.get().load(BaseUrl().ServerUrl + "/image_tweet/"+tweet.tweetImage).into(tweet_detail_image)
        }

        if (tweet.isLike == 1){
            detail_btn_like.isChecked = true
        }

        val dateString = SimpleDateFormat("MM/dd/yyyy").format(tweet.createDate)
        tweet_detail_create_date.text = dateString

        getDataComment(tweet)
        saveComment(tweet)
    }

    private fun saveComment(tweet: Tweet){

        btn_send_comment.setOnClickListener {
            val commentStatus = ed_tweet_comment.text
            if(commentStatus.equals("") || commentStatus == null){
                Toast.makeText(this, "fill ", Toast.LENGTH_SHORT).show()
            }else{
                val comment = Comment()
                comment.commentStatus = commentStatus.toString()
                comment.commentTweet = tweet

                val user = Users()
                user.id = sharedPreferences?.getString("userId", null)!!.toInt()

                comment.commentBy = user
                comment.message = "SAVE_COMMENT"
                handleSaveComment(comment)

                ed_tweet_comment.setText("")
            }
        }
    }

    private fun handleSaveComment(comment: Comment){
        apiComment.saveComment(comment).enqueue(object : Callback<MutableList<Comment>>{
            override fun onResponse(call: Call<MutableList<Comment>>, response: Response<MutableList<Comment>>) {
                comments = response.body()!!

                for (comment : Comment in comments!!){
                    Log.d("comment", comment.commentBy?.screenName.toString())
                }

                handleComment(comments!!)
            }

            override fun onFailure(call: Call<MutableList<Comment>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    override fun selectedItem(action: String, position: Int) {
        val comment = comments?.get(position)

        //Toast.makeText(this, "comment id : "+comment?.id, Toast.LENGTH_SHORT).show()

        apiComment.deleteComment("DELETE_COMMENT_BY_ID", comment!!.id, tweet.id).enqueue(object : Callback<MutableList<Comment>>{
            override fun onResponse(
                call: Call<MutableList<Comment>>,
                response: Response<MutableList<Comment>>
            ) {
                comments = response.body()

                handleComment(comments!!)
            }

            override fun onFailure(call: Call<MutableList<Comment>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}