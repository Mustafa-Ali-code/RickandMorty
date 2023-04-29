package com.example.milestone_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class characterAdapter(private val characterList: List<RMCharacter>) : RecyclerView.Adapter<characterAdapter.ViewHolder>(){
    data class RMCharacter(
        val name1: String,
        val name2: String,
        val status: String,
        val episode: String,
        val correctNameIndex: Int,
        val imageUrl: String
    )

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val characterEpisode: TextView = view.findViewById(R.id.episode)
        val characterStatus: TextView = view.findViewById(R.id.status)
        val characterImage: ImageView = view.findViewById(R.id.character_image)
        val answer1: Button = view.findViewById(R.id.btn_1)
        val answer2: Button = view.findViewById(R.id.btn_2)

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
        holder.answer1.text = character.name1
        holder.answer2.text = character.name2

        holder.answer1.setOnClickListener {
            checkAnswer(character, 1)
        }

        holder.answer2.setOnClickListener {
            checkAnswer(character, 2)
        }
    }

    private fun checkAnswer(character: RMCharacter, selectedAnswerIndex: Int) {
        if (selectedAnswerIndex == character.correctNameIndex) {
            // Correct answer selected
            // Increment correct answer count or perform any other required actions
        } else {
            // Incorrect answer selected
        }
    }

    override fun getItemCount() = characterList.size
}