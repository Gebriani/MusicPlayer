package com.orcaella.mymusicplayer.presentation.searchmusic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.orcaella.mymusicplayer.R
import com.orcaella.mymusicplayer.data.MusicDetail
import kotlinx.android.synthetic.main.list_item_song.view.*

class MainItemAdapter(
    private val musicList: ArrayList<MusicDetail>
) : RecyclerView.Adapter<MainItemAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(music: MusicDetail) {
            itemView.titleTxtView.text = music.trackName
            itemView.artistTxtView.text = music.artistName
            itemView.albumTxtView.text = music.collectionName
            Glide.with(itemView.imageView.context)
                .load(music.artworkUrl60)
                .into(itemView.imageView)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_song, parent, false)
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(musicList[position])

    override fun getItemCount(): Int = musicList.size ?: 0


    fun addData(list: List<MusicDetail>) {
        musicList.clear()
        musicList.addAll(list)
    }

}