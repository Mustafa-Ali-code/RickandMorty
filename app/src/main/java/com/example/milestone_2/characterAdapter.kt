package com.example.milestone_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class characterAdapter(private val characterList: List<RMCharacter>) : RecyclerView.Adapter<characterAdapter.ViewHolder>(){
    data class RMCharacter(
        val name: String,
        val imageUrl: String,
        val status: String,
        val episode: String
    )

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val characterEpisode: TextView = view.findViewById(R.id.episode)
        val characterStatus: TextView = view.findViewById(R.id.status)
        val characterImage: ImageView = view.findViewById(R.id.character_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rick_and_morty_character, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characterList[position]

        Glide.with(holder.itemView)
            .load(character.imageUrl)
            .centerCrop()
            .into(holder.characterImage)

        holder.characterStatus.text = "Status: ${character.status}"
        holder.characterEpisode.text = "Episode: ${character.episode}"
    }


    override fun getItemCount() = characterList.size
}