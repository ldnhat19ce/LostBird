package com.ldnhat.adapter

import android.content.Context
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.cardview.widget.CardView
import androidx.core.util.valueIterator
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
    private var iTweetAdapter:ITweetAdapter
    internal var itemStateArray:SparseBooleanArray = SparseBooleanArray()

    constructor(context: Context, tweets: MutableList<Tweet>, iTweetAdapter: ITweetAdapter){
        this.context = context
        this.tweets = tweets
        this.iTweetAdapter = iTweetAdapter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetItemViewHolder {
        val v:View = LayoutInflater.from(context).inflate(R.layout.tweet_information, parent, false)

        return TweetItemViewHolder(v)
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    override fun onBindViewHolder(holder: TweetItemViewHolder, position: Int) {
        var tweet = tweets[position]
        holder.bind(position, tweet, itemStateArray, iTweetAdapter)

        val number: BooleanIterator = itemStateArray.valueIterator()

        while (number.hasNext()){
            Log.d("position ", "" + position + " value: " + number.next())
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
        var btnLike:CheckBox
        var btnShare:ImageView

        constructor(v: View) : super(v){
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
            //this.setIsRecyclable(false)
        }

        fun bind(
            position: Int,
            tweet: Tweet,
            itemStateArray: SparseBooleanArray,
            iTweetAdapter: ITweetAdapter
        ){

            tweetStatus.text = tweet.tweetStatus
            tweetUsername.text = tweet.user?.username
            tweetScreenName.text = tweet.user?.screenName

            val dateString = SimpleDateFormat("MM/dd/yyyy").format(tweet.createDate?.let { Date(it) })
            tweetCreateDate.text = dateString
            Picasso.get().load(tweet.user?.profileImage).into(tweetProfileImage)

            //check image is exist
            if (tweet.tweetImage.equals("") || tweet.tweetImage == null){
                tweetCardView.visibility = View.GONE
            }else{

                tweetCardView.visibility = View.VISIBLE
                Picasso.get().load(BaseUrl().ServerUrl + "/image_tweet/" + tweet.tweetImage).into(
                    tweetImage
                )
            }

            //check like user
            if (tweet.isLike == 1){
                //btnLike.isChecked = true
                itemStateArray.put(adapterPosition, true)
                //.Log.d("adapter", tweet.like?.id.toString() +"tweet id"+tweet.id)
            }else{
                itemStateArray.put(adapterPosition, false)
            }

            btnLike.setOnClickListener {
                iTweetAdapter.checkLike(tweet.isLike!!, tweet, itemStateArray, adapterPosition)
            }

            if (itemStateArray.get(position, true)){
                btnLike.isChecked = true
            }else{
                btnLike.isChecked = false
            }

            itemView.setOnClickListener {
                iTweetAdapter.ItemClicklistener(tweet, position)
            }
        }


    }
}