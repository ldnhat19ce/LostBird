package com.ldnhat.ui.user

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ldnhat.api.BaseConfig
import com.ldnhat.api.users.APIUsers
import com.ldnhat.lostbird.R
import com.ldnhat.model.Users
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.setting_user_activity.*

class SettingUserActivity : AppCompatActivity() {

    private val REQUEST_CODE_ASK_PERMISSIONS = 123
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.MANAGE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    private lateinit var user:Users
    private var apiUsers:APIUsers = BaseConfig().getInstance().create(APIUsers::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_user_activity)

        setSupportActionBar(toolbar_setting_user)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getDataUser()

        profile_edit_cover.setOnClickListener {
            requestPermission()
            selectImage(this)
        }
    }

    private fun getDataUser(){
        val intent = intent

        user = intent.getParcelableExtra<Users>("USER") as Users

        Picasso.get().load(user.profileImage).into(profile_edit_image)
        Picasso.get().load(user.profileCover).into(profile_edit_cover)

        ed_setting_profile_screenname.setText(user.screenName)
        ed_setting_profile_bio.setText(user.bio)
        ed_setting_profile_country.setText(user.country)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.save_edit_setting_user -> {
                Toast.makeText(this, "save", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun selectImage(context: Context){
        val options: Array<String> = arrayOf("Take Photo", "Choose from Gallery", "Cancel")

        val builder:AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setTitle("Chọn phương thức")

        builder.setItems(options) { dialog, item ->
            if (options[item].equals("Take Photo")) {
                val takePicture: Intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePicture, 0)
            }
        }
        builder.show()
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

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
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