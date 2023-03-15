package com.gecb.attendy

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("android:src")
fun setImageUrl(view: ImageView, url: String?) {
    Glide.with(view.context).load(url).into(view)
}

@BindingAdapter("drawableStartCompat")
fun TextView.setDrawableRight(resourceId: Int) {
    val drawable = ContextCompat.getDrawable(context, resourceId)
    setIntrinsicBounds(drawable)
    val drawables = compoundDrawables
    setCompoundDrawables(drawable,null, null, null)
}

private fun setIntrinsicBounds(drawable: Drawable?) {
    drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
}