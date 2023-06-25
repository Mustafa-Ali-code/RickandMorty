package com.example.milestone_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONTokener

class MainActivity : AppCompatActivity() {

    private lateinit var rvCharacters: RecyclerView
    private lateinit var scoreTextView: TextView
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvCharacters = findViewById(R.id.character_list)
        scoreTextView = findViewById(R.id.score_button)

        getCharacters()

        // Set up score button to update score
        val scoreButton = findViewById<Button>(R.id.score_button)
        scoreButton.setOnClickListener {
            Log.d("Score", "Current score: $score")
        }
    }

    private fun getCharacters() {
        val client = AsyncHttpClient()
        val link = "https://rickandmortyapi.com/api/character/?page=1"
        client.get(link, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.d("Character Fetching", "response successful")
                Log.d("Dog", "response successful$json")

                val characterArray = json.jsonObject.getJSONArray("results")
                val characters = mutableListOf<CharacterAdapter.RMCharacter>()

                val randomizedIndexes = (0 until characterArray.length()).shuffled().take(10)

                for (i in randomizedIndexes) {
                    val characterObj = characterArray.getJSONObject(i)
                    val name = characterObj.getString("name")
                    val imageUrl = characterObj.getString("image")
                    val status = characterObj.getString("status")
                    val episodeArray = characterObj.getJSONArray("episode")
                    var episode = ""
                    if (episodeArray.length() > 0) {
                        episode = episodeArray.getString(0)
                        episode = episode.substring(episode.lastIndexOf("/") + 1)
                    }
                    val correctIndex = (0..1).random() // Generate random index for correct answer (0 or 1)
                    val speciesValue = characterObj.optString("species")
                    val speciesArray = if (speciesValue.isNotEmpty()) JSONArray().put(speciesValue) else JSONArray()
                    val correctAnswer = speciesArray.getString(0)
                    val wrongAnswer = if (correctIndex == 0) {
                        speciesArray.getString(0)
                    } else {
                        characterArray.getJSONObject((0 until characterArray.length()).random()).optJSONArray("species")?.getString(0) ?: ""
                    }
                    val isAnswer1Correct = (correctIndex == 0)
                    characters.add(CharacterAdapter.RMCharacter(
                        name,
                        characterArray.getJSONObject((0 until characterArray.length()).random()).getString("name"),
                        name,
                        isAnswer1Correct,
                        !isAnswer1Correct,
                        imageUrl,
                        episode
                    ))
                }

                val adapter = CharacterAdapter(characters) { isCorrect ->
                    if (isCorrect) {
                        score++ // Increment score if answer is correct
                        scoreTextView.text = "Score: $score"
                    }
                }
                rvCharacters.adapter = adapter
                rvCharacters.layoutManager = LinearLayoutManager(this@MainActivity)
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, throwable: Throwable?) {
                Log.d("Character Error", "errorResponse")
            }
        })
    }
}
