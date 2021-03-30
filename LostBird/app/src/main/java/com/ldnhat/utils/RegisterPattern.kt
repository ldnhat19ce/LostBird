package com.ldnhat.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterPattern {

    val VALID_USERNAME_REGEX:Pattern = Pattern.compile("^[a-zA-Z0-9]+\$", Pattern.CASE_INSENSITIVE)
    val VALID_EMAIL_ADDRESS_REGEX:Pattern = Pattern.compile("^(.+)@(.+)\$", Pattern.CASE_INSENSITIVE)
    val VALID_PASSWORD_REGEX:Pattern =
        Pattern.compile("^[a-zA-Z0-9].{8,20}\$", Pattern.CASE_INSENSITIVE)

    fun registerValidation(stringStr:String, regex:Pattern):Boolean{
        val matcher:Matcher = regex.matcher(stringStr)

        return matcher.matches()
    }
}