package com.pointlessapps.videosapp.adapters

import android.annotation.SuppressLint
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pointlessapps.videosapp.databinding.ItemVideoBinding
import com.pointlessapps.videosapp.models.Video
import com.squareup.picasso.Picasso

class AdapterVideos : RecyclerView.Adapter<AdapterVideos.ViewHolder>() {

    private val _items = mutableListOf<Video>()
    private var onItemClickedListener: ((Video) -> Unit)? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemVideoBinding.inflate(LayoutInflater.from(parent.context))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textTitle.text = _items[position].title
        holder.binding.textSubtitle.text = _items[position].subtitle
        holder.binding.textDate.text = DateUtils.getRelativeTimeSpanString(
            _items[position].date.time,
            System.currentTimeMillis(),
            DateUtils.MINUTE_IN_MILLIS
        )
        Picasso.get().load(_items[position].imageUrl)
            .into(holder.binding.imageThumbnail)

        holder.binding.root.setOnClickListener {
            onItemClickedListener?.invoke(_items[position])
        }
    }

    override fun getItemCount() = _items.size
    override fun getItemId(position: Int) = _items[position].id.toLong()

    @SuppressLint("NotifyDataSetChanged")
    fun update(list: List<Video>) {
        _items.clear()
        _items.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: (Video) -> Unit) {
        this.onItemClickedListener = onItemClickListener
    }

    class ViewHolder(val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root)
}