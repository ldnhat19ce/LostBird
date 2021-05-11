package com.ldnhat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.ldnhat.lostbird.R
import com.ldnhat.model.Comment
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.ItemCommentViewHolder>{

    private var context:Context
    private var comments:MutableList<Comment>
    private var iCommentAdapter:ICommentAdapter

    constructor(context: Context, comments: MutableList<Comment>, iCommentAdapter:ICommentAdapter) : super() {
        this.context = context
        this.comments = comments
        this.iCommentAdapter = iCommentAdapter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCommentViewHolder {
        val v:View = LayoutInflater.from(context).inflate(R.layout.comment_information, parent, false)

        return ItemCommentViewHolder(v, iCommentAdapter)
    }

    override fun onBindViewHolder(holder: ItemCommentViewHolder, position: Int) {
        val comment = comments[position]

        holder.bindView(position, comment)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    class ItemCommentViewHolder : RecyclerView.ViewHolder, View.OnClickListener, PopupMenu.OnMenuItemClickListener{

        var profileImageComment:ImageView
        var screenNameComment:TextView
        var usernameComment:TextView
        var dateComment:TextView
        var commentStatus:TextView
        var btnShowPopupMenu:ImageButton
        var iCommentAdapter: ICommentAdapter

        constructor(v: View, iCommentAdapter: ICommentAdapter) : super(v){

            profileImageComment = v.findViewById(R.id.tweet_detail_profile_image_comment)
            screenNameComment = v.findViewById(R.id.tweet_detail_screenname_comment)
            usernameComment = v.findViewById(R.id.tweet_detail_username_comment)
            dateComment = v.findViewById(R.id.tweet_detail_create_date_comment)
            commentStatus = v.findViewById(R.id.tweet_detail_comment_status)
            btnShowPopupMenu = v.findViewById(R.id.btn_select_item_comment)
            btnShowPopupMenu.setOnClickListener(this)
            this.iCommentAdapter = iCommentAdapter
        }

        fun bindView(position: Int, comment : Comment){
            Picasso.get().load(comment.commentBy?.profileImage).into(profileImageComment)

            commentStatus.text = comment.commentStatus
            screenNameComment.text = comment.commentBy?.screenName
            usernameComment.text = "@"+comment.commentBy?.username
        }

        override fun onClick(v: View?) {
            if (v != null) {
                showPopupMenu(v)
            }
        }

        private fun showPopupMenu(v : View){
            val popupMenu:PopupMenu = PopupMenu(v.context, v)
            popupMenu.inflate(R.menu.popup_comment_menu)
            popupMenu.setOnMenuItemClickListener(this)
            popupMenu.show()

        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            when(item?.itemId){
                R.id.action_popup_delete -> {
                    iCommentAdapter.selectedItem("DELETE", adapterPosition)
                    return true
                }
            }
            return false
        }
    }
}