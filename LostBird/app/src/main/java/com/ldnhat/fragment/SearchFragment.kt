package com.ldnhat.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ldnhat.adapter.HashtagAdapter
import com.ldnhat.adapter.IUserAdapter
import com.ldnhat.adapter.SearchAdapter
import com.ldnhat.api.BaseConfig
import com.ldnhat.api.trend.TrendAPI
import com.ldnhat.api.users.APIUsers
import com.ldnhat.lostbird.R
import com.ldnhat.model.Trend
import com.ldnhat.model.Users
import com.ldnhat.ui.user.ProfileActivity
import kotlinx.android.synthetic.main.fragment_home_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment(), IUserAdapter {

    private lateinit var searchAdapter: SearchAdapter
    private var recyclerView: RecyclerView? = null
    private lateinit var rootView:View
    private var users:MutableList<Users> = ArrayList()
    private val userAPI:APIUsers = BaseConfig().getInstance().create(APIUsers::class.java)
    private val trendAPI:TrendAPI = BaseConfig().getInstance().create(TrendAPI::class.java)
    private var trends:MutableList<Trend> = ArrayList()
    private var rvHashtag:RecyclerView? = null
    private lateinit var hashtagAdapter: HashtagAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        rootView = inflater.inflate(R.layout.fragment_home_search, container, false)
        recyclerView = rootView.findViewById(R.id.rv_search)
        recyclerView?.visibility = View.GONE

        rvHashtag = rootView.findViewById(R.id.rv_hashtag)
        getDataUser()
        buildRecyclerView()
        getDataHashtag()
        return rootView
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        var searchItem:MenuItem = menu.findItem(R.id.search)

        var searchView:SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.equals("")){
                    recyclerView?.visibility = View.GONE
                    cons_xh.visibility = View.VISIBLE
                }else{
                    recyclerView?.visibility = View.VISIBLE
                    cons_xh.visibility = View.GONE
                    filter(newText)
                }
                return false
            }

        })
    }

    private fun filter(text:String?){

        var test:MutableList<Users> = ArrayList()

        for (item : Users in users){
            if (item.screenName?.toLowerCase(Locale.ROOT)?.contains(text!!.toLowerCase(Locale.ROOT)) == true) {
                test.add(item)
            }
        }

        if (test.isEmpty()){
            //Toast.makeText(rootView.context, "No Data Found..", Toast.LENGTH_SHORT).show();
        }else{
            searchAdapter.filterList(test)
        }
    }

    private fun buildRecyclerView(){
        searchAdapter = SearchAdapter(rootView.context, users, this)
        recyclerView?.layoutManager = LinearLayoutManager(rootView.context)
        recyclerView?.adapter = searchAdapter
        searchAdapter.notifyDataSetChanged()
    }

    private fun getDataUser(){
        userAPI.findAll("FIND_ALL_USER").enqueue(object : Callback<MutableList<Users>>{
            override fun onResponse(
                call: Call<MutableList<Users>>,
                response: Response<MutableList<Users>>
            ) {
                users = response.body()!!
            }

            override fun onFailure(call: Call<MutableList<Users>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun findById(user: Users, position: Int) {
        val intent = Intent(rootView.context, ProfileActivity::class.java)
        intent.putExtra("USER", user)
        startActivity(intent)
    }

    private fun getDataHashtag(){
        trendAPI.findAll("FIND_ALL_HASHTAG").enqueue(object : Callback<MutableList<Trend>>{
            override fun onResponse(
                call: Call<MutableList<Trend>>,
                response: Response<MutableList<Trend>>
            ) {
                trends = response.body()!!
                handleHashtag(trends)
            }

            override fun onFailure(call: Call<MutableList<Trend>>, t: Throwable) {
               t.printStackTrace()
            }
        })
    }

    private fun handleHashtag(trends:MutableList<Trend>){
        hashtagAdapter = HashtagAdapter(rootView.context, trends)
        rvHashtag?.layoutManager = LinearLayoutManager(rootView.context)
        rvHashtag?.adapter = hashtagAdapter
        hashtagAdapter.notifyDataSetChanged()
    }
}