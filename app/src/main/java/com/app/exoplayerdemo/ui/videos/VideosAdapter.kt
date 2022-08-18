package com.app.exoplayerdemo.ui.videos

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.app.domain.entities.VideoInfo
import com.app.exoplayerdemo.databinding.ItemVideosBinding
import com.bumptech.glide.Glide

class VideosAdapter(val onItemCLick: (Int, VideoInfo) -> Unit) :
    RecyclerView.Adapter<VideosAdapter.ViewHolder>() {

    private val videos: MutableList<VideoInfo> = mutableListOf()

    inner class ViewHolder(val binding: ItemVideosBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.itemMain.setOnClickListener {
                onItemCLick.invoke(absoluteAdapterPosition, videos[absoluteAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemVideosBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.run {
            val videoInfo = videos[position]
            textTitle.text = "${videoInfo.title}"
            textProduction.text = "${videoInfo.production}"
            textIsLive.isVisible = videoInfo.isLive

            val uri = Uri.parse("file:///android_asset/images/${videoInfo.image}")
            Glide.with(root)
                .load(uri)
                .into(imageView)
        }
    }

    override fun getItemCount(): Int = videos.size

    fun addAll(list: List<VideoInfo>) {
        videos.clear()
        videos.addAll(list.toMutableList())
        notifyDataSetChanged()
    }
}
