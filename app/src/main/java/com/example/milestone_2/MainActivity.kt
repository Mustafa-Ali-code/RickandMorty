package com.example.milestone_2


import android.annotation.SuppressLint
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private lateinit var characterList: MutableList<String>
    private lateinit var rvCharacters: RecyclerView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvCharacters = findViewById(R.id.character_list)
        characterList = mutableListOf()

        val image = findViewById<ImageView>(R.id.character_image)
        val status = findViewById<TextView>(R.id.status)
        val episode = findViewById<TextView>(R.id.episode)

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

                for (i in 0 until characterArray.length()) {
                    val characterObj = characterArray.getJSONObject(i)
                    val name = characterObj.getString("name")
                    val imageUrl = characterObj.getString("image")
                    val status = characterObj.getString("status")
                    val episode = characterObj.getString("episode").substring(47, 49) //uming you want the first episode
                    characters.add(characterAdapter.RMCharacter(name, imageUrl, status, episode))
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
}