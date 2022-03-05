package com.szirakiapps.refreshratereset

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.szirakiapps.refreshratereset.databinding.ActivityMainBinding
import com.szirakiapps.refreshratereset.takt.Takt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isInProgress = false
    private lateinit var takt: Takt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        takt = Takt()
        takt.prepare(binding.txtFps)
        takt.play()

        binding.btnReset.setOnClickListener {
            if (isInProgress) {
                return@setOnClickListener
            }
            executeActions()
        }
    }

    override fun onDestroy() {
        takt.finish()
        super.onDestroy()
    }

    private fun executeActions() {
        isInProgress = true

        val params = binding.progressForeground.layoutParams
        params.width = params.height
        binding.progressForeground.layoutParams = params

        actionList.forEachIndexedDelayed(1000) { i, action ->

            action.function.invoke(this)

            if (isDestroyed) {
                isInProgress = false
                return@forEachIndexedDelayed
            }

            showProgress(action)

            if (i == actionList.lastIndex) {
                isInProgress = false
            }
        }
    }

    private fun showProgress(action: Action) {
        val fullWidth = binding.progressBackground.width
        val minWidth = binding.progressForeground.height

        val calcNewWidth = (minWidth + (fullWidth - minWidth) * action.percentage).toInt()
        val newWidth = Math.min(fullWidth, calcNewWidth)

        binding.txtProgress.text = action.title
        animateWidth(binding.progressForeground, binding.progressForeground.width, newWidth)
    }
}