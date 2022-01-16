package com.makhalibagas.mygithubuser.utils

import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.makhalibagas.mygithubuser.R
import de.hdodenhof.circleimageview.CircleImageView

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun CircleImageView.loadCircleImage(url:String) {
    Glide.with(this)
        .load(url)
        .apply(
            RequestOptions
            .placeholderOf(R.color.gray)
            .error(R.color.gray)
        )
        .into(this)
}

