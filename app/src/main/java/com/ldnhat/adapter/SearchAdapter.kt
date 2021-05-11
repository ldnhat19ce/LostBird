package com.ldnhat.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.ldnhat.lostbird.R
import com.ldnhat.model.Users
import com.squareup.picasso.Picasso

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ItemSearchViewHolder>{

    private var context:Context
    private var users:MutableList<Users>
    private var iUserAdapter:IUserAdapter

    constructor(context: Context, users: MutableList<Users>, iUserAdapter: IUserAdapter) : super() {
        this.context = context
        this.users = users
        this.iUserAdapter = iUserAdapter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSearchViewHolder {
        val v:View = LayoutInflater.from(context).inflate(R.layout.search_user, parent, false)

        return ItemSearchViewHolder(v)
    }

    override fun onBindViewHolder(holder: ItemSearchViewHolder, position: Int) {
        val user = users[position]
        holder.bindView(user, iUserAdapter)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun filterList(filterList:MutableList<Users>){
        users = filterList
        notifyDataSetChanged()
    }

    class ItemSearchViewHolder : RecyclerView.ViewHolder{
       private var txtSearchScreennameUser:TextView
       private var txtSearchUsername:TextView
       private var imgSearchProfileImage:ImageView

       constructor( v: View) : super(v) {
           txtSearchScreennameUser = v.findViewById(R.id.txt_search_screen_name_user)
           txtSearchUsername = v.findViewById(R.id.txt_search_username)
           imgSearchProfileImage = v.findViewById(R.id.image_search_user)
       }

        @SuppressLint("SetTextI18n")
        fun bindView(user:Users, iUserAdapter: IUserAdapter){
            txtSearchScreennameUser.text = user.screenName
            txtSearchUsername.text = "@"+user.username
            Picasso.get().load(user.profileImage).into(imgSearchProfileImage)

            itemView.setOnClickListener {
                iUserAdapter.findById(user, adapterPosition)
            }
        }
   }
}