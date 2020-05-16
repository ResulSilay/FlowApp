package app.flow.ui.helper.binding

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.flow.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("adapter")
fun adapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    recyclerView.adapter = adapter
    recyclerView.setHasFixedSize(true)
}

@BindingAdapter("listener")
fun listener(swipeRefreshLayout: SwipeRefreshLayout, status: Boolean) {
    swipeRefreshLayout.isRefreshing = status
}

@BindingAdapter("refresh")
fun refreshing(swipeRefreshLayout: SwipeRefreshLayout, status: ObservableBoolean) {
    swipeRefreshLayout.isRefreshing = status.get()
}

@BindingAdapter("image")
fun image(circleImageView: CircleImageView, bitmap: Bitmap?) {
    if (bitmap != null) {
        circleImageView.setImageBitmap(bitmap)
    } else {
        circleImageView.setImageResource(R.drawable.ic_profile_default)
    }
}

@BindingAdapter("image")
fun image(circleImageView: CircleImageView, url: String?) {
    url?.let { it ->
        if (it.isNotEmpty())
            Picasso.get().load(it).into(circleImageView)
        else
            circleImageView.setImageResource(R.drawable.ic_profile_default)
    }
}

@BindingAdapter("image")
fun image(imageView: ImageView, url: String?) {
    url?.let { it ->
        if (it.isNotEmpty())
            Picasso.get().load(it).into(imageView)
        else
            imageView.setImageResource(0)
    }
}

@BindingAdapter("image")
fun image(imageView: ImageView, bitmap: Bitmap?) {
    if (bitmap != null) {
        imageView.setImageBitmap(bitmap)
    } else {
        imageView.setImageResource(0)
    }
}


@BindingAdapter("isVisible")
fun isVisible(view: View, status: Boolean) {
    view.visibility = if (status) View.VISIBLE else View.GONE
    try {
        val progress = view.findViewById<ContentLoadingProgressBar>(R.id.include_loading_progress)
        val layoutNetwork = view.findViewById<LinearLayout>(R.id.include_loading_network)

        val connectivityManager = view.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            layoutNetwork.visibility = View.GONE
            progress.visibility = View.VISIBLE
        } else {
            layoutNetwork.visibility = View.VISIBLE
            progress.visibility = View.GONE
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
