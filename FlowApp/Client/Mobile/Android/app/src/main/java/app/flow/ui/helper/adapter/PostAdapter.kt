package app.flow.ui.helper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.flow.R
import app.flow.data.network.dto.response.PostResponseDto
import app.flow.util.DateUtil
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class PostAdapter(private val listener: ClickListener) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private var itemList: ArrayList<PostResponseDto> = arrayListOf()

    fun set(itemList: ArrayList<PostResponseDto>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    fun setAll(itemList: ArrayList<PostResponseDto>) {
        this.itemList.addAll(this.itemList.size, itemList)
        notifyDataSetChanged()
    }

    fun get(position: Int): PostResponseDto? {
        return itemList[position]
    }

    interface ClickListener {
        fun onClick(position: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val layout: LinearLayout = view.findViewById(R.id.listLayout)
        private val img: CircleImageView = view.findViewById(R.id.img_post_user)
        private val txtName: TextView = view.findViewById(R.id.txt_view_post_username)
        private val txtContext: TextView = view.findViewById(R.id.txt_view_post_context)
        private val imgPicture: ImageView = view.findViewById(R.id.img_view_post_pic)
        private val ratingBar: RatingBar = view.findViewById(R.id.rating_view_post_bar)
        private val txtRate: TextView = view.findViewById(R.id.txt_view_post_rate)
        private val txtDateTime: TextView = view.findViewById(R.id.txt_view_post_date)

        fun bind(
            item: PostResponseDto?,
            position: Int,
            listener: ClickListener
        ) {
            if (item != null) {
                try {
                    txtName.text = item.username
                    txtContext.text = item.content
                    txtRate.text = item.rateValue.toString()
                    ratingBar.rating = item.rateValue

                    item.createdDateTime.let {
                        txtDateTime.text = DateUtil.getDateFormat(it)
                    }

                    item.img.let {
                        if (it != null)
                            Picasso.get().load(it).into(img)
                        else
                            Picasso.get().load(R.drawable.ic_profile_default).into(img)
                    }

                    item.picture?.let { it ->
                        if (it.isNotEmpty())
                            Picasso.get().load(it).into(imgPicture)
                        else
                            imgPicture.setImageResource(0)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            layout.setOnClickListener { listener.onClick(position) }
        }
    }

    override fun getItemId(position: Int): Long {
        return itemList[position].postId.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_post, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position], position, listener)
    }
}