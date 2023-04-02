package com.example.algorytmy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import java.util.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val main_but = findViewById<Button>(R.id.glowny_button)
        val ile_razy = findViewById<EditText>(R.id.ile_razy)

        val kr_cb = findViewById<CheckBox>(R.id.karpa_rabina_cb)
        val bm_cb = findViewById<CheckBox>(R.id.boyera_i_moorea_cb)
        val kmp_cb = findViewById<CheckBox>(R.id.kmp_cb)

        val kr_wynik = findViewById<TextView>(R.id.karpa_rabina_wynik)
        val bm_wynik = findViewById<TextView>(R.id.boyera_i_moorea_wynik)
        val kmp_wynik = findViewById<TextView>(R.id.kmp_wynik)

        main_but.setOnClickListener {
            if(ile_razy.text.toString() == ""){
                ile_razy.setError("To pole nie może być puste!")
            }
            else if(ile_razy.text.toString().toInt() <= 0){
                ile_razy.setError("W tym polu nie może być liczba mniejsza, ani równa 0!")
            }
            else{
                val tekst = generateRandomString()
                val wzorzec = generatePattern()
                println("Tekst: " + tekst)
                println("Wzorzec: " + wzorzec)
                if(kr_cb.isChecked){
                    val czas = measureTimeMillis {
                        algorytm_karpa_rabina(tekst, wzorzec, ile_razy.text.toString().toInt())
                    }
                    kr_wynik.text = "$czas ms"
                }

                if(bm_cb.isChecked){
                    val czas = measureTimeMillis {
                        algorytm_boyeramoorea(tekst, wzorzec, ile_razy.text.toString().toInt())
                    }
                    bm_wynik.text = "$czas ms"
                }

                if(kmp_cb.isChecked){

                }
            }
        }
    }

    fun algorytm_karpa_rabina(text: String, pattern: String, repeatCount: Int){
        var pozycja = -1
        for (powtorzenie in 0 until repeatCount){
            val prime = 101
            val n = text.length
            val m = pattern.length
            val d = 256

            var p = 0
            var t = 0
            var h = 1

            for (i in 0 until m) {
                p = (d * p + pattern[i].toInt()) % prime
                t = (d * t + text[i].toInt()) % prime
            }

            for (i in 0 until m - 1) {
                h = (h * d) % prime
            }

            for (i in 0..n - m) {
                if (p == t) {
                    var j = 0
                    while (j < m && text[i + j] == pattern[j]) {
                        j++
                    }
                    if (j == m) {
                        pozycja = i
                    }
                }
                if (i < n - m) {
                    t = (d * (t - text[i].toInt() * h) + text[i + m].toInt()) % prime
                    if (t < 0) {
                        t += prime
                    }
                }
            }
        }
        if(pozycja != -1){
            println("Algorytm Karpa-Rabina znalazł wzorzec w tekście na pozycji: $pozycja")
        }
    }

    fun algorytm_boyeramoorea(text: String, pattern: String, ile_razy: Int) {
        var pozycja = -1
        for(b in 0 until ile_razy){
            val n = text.length
            val m = pattern.length

            // Utwórz tablicę przesunięć
            val shifts = IntArray(256) { m }
            for (i in 0 until m - 1) {
                shifts[pattern[i].toInt()] = m - i - 1
            }

            // Szukaj wzorca w tekście
            var i = m - 1
            var j = i
            while (i < n) {
                if (text[i] == pattern[j]) {
                    if (j == 0) {
                        pozycja = i // Znaleziono wzorzec
                    } else {
                        i--
                        j--
                    }
                } else {
                    i += shifts[text[i].toInt()]
                    j = m - 1
                }
            }
        }
        if(pozycja != -1){
            println("Algorytm BayerMoore'a znalazł wzorzec w tekście na pozycji: $pozycja")
        }
    }

    fun generateRandomString(): String {
        val chars = "abcdefghijklmnopqrstuvwxyz0123456789"
        val stringBuilder = StringBuilder()
        val random = Random()

        for (i in 0 until 100) {
            val index = random.nextInt(chars.length)
            stringBuilder.append(chars[index])
        }

        return stringBuilder.toString()
    }

    fun generatePattern(): String{
        val chars = "abcdefghijklmnopqrstuvwxyz0123456789"
        val stringBuilder = StringBuilder()
        val random = Random()

        for (i in 0 until 2) {
            val index = random.nextInt(chars.length)
            stringBuilder.append(chars[index])
        }

        return stringBuilder.toString()
    }
}