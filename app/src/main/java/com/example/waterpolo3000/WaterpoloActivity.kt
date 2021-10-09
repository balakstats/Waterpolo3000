package com.example.waterpolo3000

import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.example.waterpolo3000.game.GameControl
import com.example.waterpolo3000.databinding.ActivityGardenBinding
import com.example.waterpolo3000.utilities.ProcessBT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WaterpoloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityGardenBinding>(this, R.layout.activity_garden)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
//        Toast.makeText(this, "hey: $keyCode, $event", Toast.LENGTH_SHORT).show()
        return when (keyCode) {
            KeyEvent.KEYCODE_PAGE_UP -> {
                GameControl.startStopCounter()
                true
            }
            KeyEvent.KEYCODE_PAGE_DOWN -> {
                GameControl.newShotclockBig()
                true
            }
            KeyEvent.KEYCODE_ESCAPE -> {
                GameControl.newShotclockSmall()
                true
            }
            KeyEvent.KEYCODE_B -> {
                true
            }
            else -> super.onKeyUp(keyCode, event)
        }
    }

}