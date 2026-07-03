package com.example.animalsounds

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech

    private val animals = listOf(
        Animal("سگ", "🐶", "هاپ هاپ، هاپ هاپ"),
        Animal("گربه", "🐱", "میائو، میائو"),
        Animal("گاو", "🐮", "موو، موو"),
        Animal("اردک", "🦆", "گاک گاک، گاک گاک"),
        Animal("خروس", "🐓", "قوقولی قوقو"),
        Animal("گوسفند", "🐑", "بع بع، بع بع"),
        Animal("شیر", "🦁", "غررر، غررر"),
        Animal("اسب", "🐴", "هیی هیی، شیهه"),
        Animal("قورباغه", "🐸", "قور قور، قور قور"),
        Animal("خوک", "🐷", "خرخر، خرخر"),
        Animal("فیل", "🐘", "بوووپ، بوووپ"),
        Animal("جغد", "🦉", "هوهو، هوهو")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, this)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = AnimalAdapter(animals) { animal ->
            playSound(animal)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale("fa"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                tts.setLanguage(Locale.US)
            }
        }
    }

    private fun playSound(animal: Animal) {
        tts.speak(animal.sound, TextToSpeech.QUEUE_FLUSH, null, animal.name)
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}
