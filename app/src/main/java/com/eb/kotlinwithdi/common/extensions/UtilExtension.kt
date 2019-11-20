package com.eb.kotlinandjavaarefriends.extensions

import java.text.SimpleDateFormat
import java.util.*

//DATE=============================================================================================

infix fun Date.of(pattern:String):String= SimpleDateFormat(pattern).format(this)
infix fun String.of(pattern:String): Date = SimpleDateFormat(pattern).parse(this)


