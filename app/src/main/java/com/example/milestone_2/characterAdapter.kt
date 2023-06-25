package com.example.milestone_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CharacterAdapter(
    private val characters: List<RMCharacter>,
    private val onCharacterSelected: (Boolean) -> Unit
) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val characterImageView: ImageView = itemView.findViewById(R.id.character_image)
        private val answerButton1: Button = itemView.findViewById(R.id.btn_1)
        private val answerButton2: Button = itemView.findViewById(R.id.btn_2)

        fun bind(character: RMCharacter) {
            Glide.with(itemView.context).load(character.imageUrl).into(characterImageView)

            answerButton1.text = character.answer1
            answerButton2.text = character.answer2

            answerButton1.setOnClickListener {
                val isAnswer1Correct = character.isAnswer1Correct
                onCharacterSelected(isAnswer1Correct)
            }

            answerButton2.setOnClickListener {
                val isAnswer2Correct = character.isAnswer2Correct
                onCharacterSelected(isAnswer2Correct)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rick_and_morty_character, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    data class RMCharacter(
        val name: String,
        val answer1: String,
        val answer2: String,
        val isAnswer1Correct: Boolean,
        val isAnswer2Correct: Boolean,
        val imageUrl: String,
        val episode: String
    )
}