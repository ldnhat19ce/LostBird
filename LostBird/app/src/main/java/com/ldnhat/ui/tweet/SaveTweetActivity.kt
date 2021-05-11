package com.ldnhat.ui.tweet

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ldnhat.api.BaseConfig
import com.ldnhat.api.tweets.APITweet
import com.ldnhat.lostbird.R
import com.ldnhat.model.Tweet
import com.ldnhat.model.Users
import com.ldnhat.ui.home.HomeActivity
import com.ldnhat.utils.FileUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_save_tweet.*
import maes.tech.intentanim.CustomIntent
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

class SaveTweetActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    private var files:MutableList<Uri> = ArrayList()
    private var selectedImage:ImageView? = null
    private val REQUEST_CODE_ASK_PERMISSIONS = 123
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.MANAGE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_tweet)

        requestPermission()

        sharedPreferences = this.getSharedPreferences("user_information", Context.MODE_PRIVATE)

        var profileImage = sharedPreferences?.getString("profileImage", null)

        Picasso.get().load(profileImage).into(imageview_user_save_tweet)

        val intent = intent
        val position = intent.getIntExtra("TAB_POSITION", 0)
        Log.d("tab", position.toString())


        btn_close_save_tweet.setOnClickListener {
            val intentback = Intent()
            intentback.putExtra("TAB_POSITION", position)
            setResult(Activity.RESULT_OK, intent)
            CustomIntent.customType(this, "up-to-bottom")
            finish()
        }

       btn_save_tweet_select_image.setOnClickListener {

           addImage()
       }

        btn_save_tweet.setOnClickListener {
            uploadImage()
        }
    }

    private fun addImage(){
        selectImage(this)
        val inflater:LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val rootView:View = inflater.inflate(R.layout.image, null)

        parent_linear_layout.addView(rootView, parent_linear_layout.childCount - 1)
        parent_linear_layout.isFocusable

        selectedImage = rootView.findViewById(R.id.number_edit_text)

    }

    private fun selectImage(context: Context){
            val pickPhoto:Intent = Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(pickPhoto, 21)
    }

    override fun finish() {
        super.finish()
        CustomIntent.customType(this, "up-to-bottom")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK){
            if (requestCode == 21 && data != null){
                val image: Uri? = data.data
                Picasso.get().load(image).into(selectedImage)

                val imgPath =FileUtil().getPath(this, image!!)
                files.add(Uri.parse(imgPath))
                Log.d("image path", imgPath.toString())
            }
        }
    }

    private fun uploadImage(){
        btn_save_tweet.startAnimation()

        val list:MutableList<MultipartBody.Part> = ArrayList()

        //list.add(prepareFilePath("file", Uri.parse("/storage/emulated/0/DCIM/Camera/IMG_20210503_213446.jpg")))

        for (uri : Uri in files){
            list.add(prepareFilePath("file", uri))
        }

        val tweet = Tweet()
        val user = Users()
        user.id = sharedPreferences?.getString("userId", null)!!.toInt()
        tweet.user = user

        Log.d("user id", tweet.user?.id.toString())

        if (ed_save_tweet_status.text == null){
            tweet.tweetStatus = " "
        }else{
            tweet.tweetStatus = ed_save_tweet_status.text.toString()
        }

        tweet.message = "SAVE_TWEET"

       // val tweetStatus:RequestBody = createPartFromString("hello, this is description speaking")

        val apiTweet = BaseConfig().getInstance().create(APITweet::class.java)

        apiTweet.saveTweet(tweet, "SAVE_TWEET").enqueue(object : Callback<Tweet>{
            override fun onResponse(call: Call<Tweet>, response: Response<Tweet>) {

                val tweet:Tweet? = response.body()

                if (files.isNotEmpty()){
                    if (tweet != null) {
                        apiTweet.saveFileTweet(list, "UPLOAD", tweet.id).enqueue(object : Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                btn_save_tweet.revertAnimation()
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                btn_save_tweet.revertAnimation()
                                t.printStackTrace()

                            }

                        })
                    }
                }

                btn_save_tweet.revertAnimation()
                val intent = intent
                val position = intent.getIntExtra("TAB_POSITION", 0)

                val intentback = Intent()
                intentback.putExtra("TAB_POSITION", position)
                setResult(Activity.RESULT_OK, intent)
                CustomIntent.customType(this@SaveTweetActivity, "up-to-bottom")

                finish()
            }

            override fun onFailure(call: Call<Tweet>, t: Throwable) {
                btn_save_tweet.revertAnimation()
                t.printStackTrace()
            }

        })


    }



    private fun prepareFilePath(partName: String, fileUri: Uri) : MultipartBody.Part{
        val file:File = File(fileUri.path)

        val requestFile:RequestBody = RequestBody.create(MediaType.parse("image/*"), file)

        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }


    // this is all you need to grant your application external storage permision
    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_ASK_PERMISSIONS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //uploadImage()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
            else -> super.onRequestPermissionsResult(requestCode, PERMISSIONS_STORAGE, grantResults)
        }
    }

}