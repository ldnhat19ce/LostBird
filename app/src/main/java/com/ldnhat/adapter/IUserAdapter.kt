package com.ldnhat.adapter

import com.ldnhat.model.Users

interface IUserAdapter {

    fun findById(user:Users, position:Int)
}