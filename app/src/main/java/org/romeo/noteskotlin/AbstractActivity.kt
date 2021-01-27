package org.romeo.noteskotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

//TODO: Make this class a template for activities
abstract class AbstractActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    abstract fun initViews()
}