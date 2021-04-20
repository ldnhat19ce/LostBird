package com.ldnhat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ldnhat.lostbird.R
import com.ldnhat.model.Tweet
import com.ldnhat.utils.BaseUrl
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class TweetAdapter : RecyclerView.Adapter<TweetAdapter.TweetItemViewHolder> {

    private var tweets:MutableList<Tweet>
    private var context:Context

    constructor(context: Context, tweets:MutableList<Tweet>){
        this.context = context
        this.tweets = tweets
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetItemViewHolder {
        val v:View = LayoutInflater.from(context).inflate(R.layout.tweet_information, parent, false)

        return TweetItemViewHolder(v)
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    override fun onBindViewHolder(holder: TweetItemViewHolder, position: Int) {0
        var tweet = tweets[position]

        holder.tweetStatus.text = tweet.tweetStatus
        holder.tweetUsername.text = tweet.user?.username
        holder.tweetScreenName.text = tweet.user?.screenName

        var dateString = SimpleDateFormat("MM/dd/yyyy").format(tweet.createDate?.let { Date(it) })
        holder.tweetCreateDate.text = dateString
        Picasso.with(context).load(tweet.user?.profileImage).into(holder.tweetProfileImage)
        if (tweet.tweetImage.equals("")){
            holder.tweetCardView.visibility = View.GONE
        }else{
            holder.tweetCardView.visibility = View.VISIBLE
            Picasso.with(context).load(BaseUrl().ServerUrl+"/image_tweet/"+tweet.tweetImage).into(holder.tweetImage)
        }

        if (tweet.like?.id != 0){
            holder.btnLike.isChecked = true
            //holder.btnLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart_2, 0, 0, 0)
        }
    }

    class TweetItemViewHolder : RecyclerView.ViewHolder{

        var tweetProfileImage:ImageView
        var tweetStatus:TextView
        var tweetUsername:TextView
        var tweetScreenName:TextView
        var tweetCreateDate:TextView
        var tweetImage:ImageView
        var tweetCardView:CardView
        var btnTweetComment:ToggleButton
        var btnRetweet:ToggleButton
        var btnLike:ToggleButton
        var btnShare:ImageView

        constructor(v : View) : super(v){
            tweetProfileImage = v.findViewById(R.id.tweet_profile_image)
            tweetScreenName = v.findViewById(R.id.tweet_screen_name)
            tweetUsername = v.findViewById(R.id.txt_tweet_username)
            tweetCreateDate = v.findViewById(R.id.txt_tweet_create_date)
            tweetStatus = v.findViewById(R.id.txt_tweet_status)
            tweetImage = v.findViewById(R.id.img_tweet)
            tweetCardView = v.findViewById(R.id.tweet_card_view)
            btnTweetComment = v.findViewById(R.id.btn_tweet_comment)
            btnRetweet = v.findViewById(R.id.btn_tweet_retweet)
            btnLike = v.findViewById(R.id.btn_like)
            btnShare = v.findViewById(R.id.btn_tweet_share)
        }
    }
}