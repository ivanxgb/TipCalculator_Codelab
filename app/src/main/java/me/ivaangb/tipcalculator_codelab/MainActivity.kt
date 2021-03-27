package me.ivaangb.tipcalculator_codelab

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import me.ivaangb.tipcalculator_codelab.databinding.ActivityMainBinding
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.inputTip.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }

        binding.calculateButton.setOnClickListener {
            hideKeyBoard(binding.inputTip)
        }
    }

    private fun showTip() {
        val snackbar =
            Snackbar.make(binding.calculateButton, "You must type a Cost", Snackbar.LENGTH_SHORT)

        val lambda = {
            snackbar.show()
            binding.inputTip.text = null
        }

        if (binding.inputTip.text.isNullOrEmpty() || binding.inputTip.text.toString()
                .toDouble() == 0.0
        ) lambda()
        else calculateTip()
    }


    private fun calculateTip() {
        val cost = binding.inputTip.text.toString().toDouble()
        val selectedId = binding.tipRadioGroup.checkedRadioButtonId

        val tipPercentage = when (selectedId) {
            R.id.radioButtonAmazing -> 0.20
            R.id.radioButtonGood -> 0.18
            else -> 0.15
        }

        var tip = tipPercentage * cost
        val roundUp = binding.roundUpSwitch.isChecked
        if (roundUp) tip = kotlin.math.ceil(tip)

        binding.totalTextView.text = NumberFormat.getCurrencyInstance().format(tip).toString()

    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            hideKeyBoard(view)
            showTip()
            return true
        }
        return false
    }

    private fun hideKeyBoard(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }
}