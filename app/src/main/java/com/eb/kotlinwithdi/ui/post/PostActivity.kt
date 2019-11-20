package com.eb.kotlinwithdi.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.eb.kotlinandjavaarefriends.extensions.hide
import com.eb.kotlinandjavaarefriends.extensions.show
import com.eb.kotlinwithdi.R
import kotlinx.android.synthetic.main.activity_post.*
import org.koin.android.ext.android.inject

class PostActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        viewModel.laoding.observe(this, Observer { if(it)progressBar.show()else progressBar.hide()})

    }
}
