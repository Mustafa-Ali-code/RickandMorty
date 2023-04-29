package com.example.milestone_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private lateinit var characterList: MutableList<String>
    private lateinit var rvCharacters: RecyclerView
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvCharacters = findViewById(R.id.character_list)
        characterList = mutableListOf()

        getCharacter()
    }

    private fun getCharacter() {
        val client = AsyncHttpClient()
        val link = "https://rickandmortyapi.com/api/character/"
        client[link, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Character Fetching", "response successful")
                Log.d("Dog", "response successful$json")

                val characterArray = json.jsonObject.getJSONArray("results")
                val characters = mutableListOf<characterAdapter.RMCharacter>()

                val randomizedIndexes = (0 until characterArray.length()).shuffled()

                for (i in randomizedIndexes) {
                    val characterObj = characterArray.getJSONObject(i)
                    val name = characterObj.getString("name")
                    val imageUrl = characterObj.getString("image")
                    val status = characterObj.getString("status")
                    val episode = characterObj.getString("episode").substring(47, 49) // Assuming you want the first episode
                    val correctNameIndex = (0..1).random() // Generate random index for correct answer (0 or 1)
                    val wrongName = characterArray.getJSONObject((0 until characterArray.length()).random()).getString("name")
                    characters.add(characterAdapter.RMCharacter(name, wrongName, status, episode, correctNameIndex, imageUrl))
                }

                val adapter = characterAdapter(characters)
                rvCharacters.adapter = adapter
                rvCharacters.layoutManager = LinearLayoutManager(this@MainActivity)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Character Error", "errorResponse")
            }
        }]
    }

    private fun checkAnswer(selectedIndex: Int, correctIndex: Int) {
        if (selectedIndex == correctIndex) {
            score++
            Log.d("Score", "Current score: $score")
        } else {
            Log.d("Score", "Current score: $score")
        }
    }


}
