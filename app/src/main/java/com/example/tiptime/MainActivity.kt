package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener{ CalculateTip()}

        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }
    }

    private fun CalculateTip(){
        val stringInTheTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTheTextField.toDoubleOrNull()

        if (cost == null) {
            binding.tipResult.text = ""
            binding.totalAmount.text = ""
            return
        }

        val tipPercentage = when(binding.tipOptions.checkedRadioButtonId){
            R.id.option_twenty_percent -> 20.0
            R.id.option_eighteen_percent -> 18.0
            else -> 15.0
        }

        var tip = (tipPercentage * cost)/100
        var totalAmount = cost + tip

        if(binding.roundUpSwitch.isChecked){
            tip = ceil(tip)
            totalAmount = ceil(totalAmount)
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        val formattedTotalAmount = NumberFormat.getCurrencyInstance().format(totalAmount)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
        binding.totalAmount.text = getString(R.string.total_amount, formattedTotalAmount)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}