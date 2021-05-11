package com.ldnhat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ldnhat.lostbird.R
import com.ldnhat.model.Trend

class HashtagAdapter : RecyclerView.Adapter<HashtagAdapter.ItemHashtagViewHolder>{

    private var context:Context
    private var trends:MutableList<Trend>

    constructor(context: Context, trends: MutableList<Trend>) : super() {
        this.context = context
        this.trends = trends
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHashtagViewHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.hashtag_information, parent, false)

        return ItemHashtagViewHolder(v)
    }

    override fun onBindViewHolder(holder: ItemHashtagViewHolder, position: Int) {
       val trend:Trend = trends[position]
        holder.bindView(trend)
    }

    override fun getItemCount(): Int {
        return trends.size
    }

    class ItemHashtagViewHolder : RecyclerView.ViewHolder{
        private var hashtagTitle:TextView
        private var tweetCount:TextView

        constructor(v: View) : super(v){
            hashtagTitle = v.findViewById(R.id.txt_hashtag_title)
            tweetCount = v.findViewById(R.id.txt_hashtag_count_tweet)
        }

        fun bindView(trend: Trend){
            hashtagTitle.text = trend.hashtag
        }
    }
}