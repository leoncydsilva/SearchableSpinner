package com.leo.searchablespinnerexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        materialButtonKotlin.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    KotlinImplementation::class.java
                )
            )
        }

        materialButtonJava.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    JavaImplementation::class.java
                )
            )
        }
    }
}
