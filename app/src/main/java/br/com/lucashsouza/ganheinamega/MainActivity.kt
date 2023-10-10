package br.com.lucashsouza.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNumber: EditText = findViewById(R.id.etNumber)
        val tvResult: TextView = findViewById(R.id.tvResult)
        val btGenerate: Button = findViewById(R.id.btGenerate)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)
        result?.let {
            tvResult.text = "Última aposta: $it"
        }

        btGenerate.setOnClickListener {
            val text = etNumber.text.toString()
            numberGenerator(text, tvResult)
        }
    }

    private fun numberGenerator(text: String, tvResult: TextView) {

        if (text.isEmpty()) {
            Toast.makeText(this, "Informe um número de 6 a 15", Toast.LENGTH_SHORT).show()
            return
        }

        val quantity = text.toInt()
        if (quantity < 6 || quantity > 15) {
            Toast.makeText(this, "Informe um número de 6 a 15", Toast.LENGTH_SHORT).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number: Int = random.nextInt(60)
            numbers.add(number + 1)

            if (numbers.size == quantity) {
                break
            }
        }

        tvResult.text = numbers.joinToString(" - ")

        prefs.edit().apply {
            putString("result", tvResult.text.toString())
            apply()
        }
    }
}