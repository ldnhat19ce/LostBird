package com.ldnhat.adapter

import android.util.SparseBooleanArray
import android.view.View
import android.widget.ToggleButton
import com.ldnhat.model.Comment
import com.ldnhat.model.Tweet

interface ITweetAdapter {

    fun checkLike(isLike : Int, tweet: Tweet, sparseBooleanArray: SparseBooleanArray, position: Int)

    fun ItemClicklistener(tweet: Tweet, position : Int)


}