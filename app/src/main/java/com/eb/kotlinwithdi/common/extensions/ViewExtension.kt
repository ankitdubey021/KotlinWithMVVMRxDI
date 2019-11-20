package com.eb.kotlinandjavaarefriends.extensions

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

operator fun TextView.invoke(func: TextView.()->Unit){
    func()
}
fun TextView.color(block: TextView.()->Int){
    setTextColor(block())
}
fun TextView.size(block: TextView.()->Float){
    setTextSize(block())
}



//Toast============================================================================================

fun Context.toast(func: Toast.()->Unit): Toast {
    val toast= Toast.makeText(this,"", Toast.LENGTH_LONG)
    toast.func()
    return toast
}

fun Toast.text(block: Toast.()->String){
    setText(block())
    show()
}


//Dialog===========================================================================================
fun Context.dialog(
        init: AlertDialog.Builder.() -> Unit = {}) {

    val builder= AlertDialog.Builder(this)
    builder.init()
    val dialog: AlertDialog =builder.create()
    dialog.show()

}
fun AlertDialog.Builder.positiveBtn(title:String, func:() -> Unit){
    setPositiveButton(title,{dialogInterface, i -> func()})
}
fun AlertDialog.Builder.negativeBtn(title:String, func:() -> Unit){
    setNegativeButton(title,{dialogInterface, i -> func()})
}