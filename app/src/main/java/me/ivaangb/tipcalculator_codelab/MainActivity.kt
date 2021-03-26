package me.ivaangb.tipcalculator_codelab

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import me.ivaangb.tipcalculator_codelab.databinding.ActivityMainBinding
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener {
            showTip()
        }
    }
    
    private fun showTip() {
        val snackbar = Snackbar.make(binding.calculateButton, "You must type a Cost", Snackbar.LENGTH_SHORT)
        if (binding.inputTip.text.isNullOrEmpty()) snackbar.show()
        else calculateTip()
    }

    private fun calculateTip() {
        val cost = binding.inputTip.text.toString().toDouble()
        val selectedId = binding.tipRadioGroup.checkedRadioButtonId

        val tipPercentage = when(selectedId){
            R.id.radioButtonAmazing -> 0.20
            R.id.radioButtonGood -> 0.18
            else -> 0.15
        }

        var tip = tipPercentage * cost
        val roundUp = binding.roundUpSwitch.isChecked

        if (roundUp) tip = kotlin.math.ceil(tip)

        binding.totalTextView.text = NumberFormat.getCurrencyInstance().format(tip).toString()

    }



}