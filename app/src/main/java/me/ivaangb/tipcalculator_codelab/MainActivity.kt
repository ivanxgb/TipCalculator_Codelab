package me.ivaangb.tipcalculator_codelab

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import me.ivaangb.tipcalculator_codelab.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.inputTip.setOnKeyListener { _, keyCode, _ -> handleKeyEvent(keyCode, binding.inputTip) }

        binding.calculateButton.setOnClickListener {
            hideKeyBoard(binding.inputTip)
            showTip(binding.inputTip)
        }
    }


    //Método que muestra el resltado
    private fun showTip(editText: EditText) {
        editText.clearFocus()
        if (validateText(editText)) calculateTip(editText)
    }

    //Método que valida si el campo de texto no está vacío o no es cero
    private fun validateText(editText: EditText): Boolean {
        val snackbar =
            Snackbar.make(editText, "You must type a Cost", Snackbar.LENGTH_SHORT)
        val lambda = {
            snackbar.show()
            editText.text = null
        }

        return if (editText.text.isNullOrEmpty() || editText.text.toString().toDouble() == 0.0){
            lambda()
            false
        } else {
            true
        }
    }

    //Método que calcula la propina y la pone en el TextView
    private fun calculateTip(editText: EditText) {
        val cost = editText.text.toString().toDouble()
        val selectedId = binding.tipRadioGroup.checkedRadioButtonId

        val tipPercentage = when (selectedId) {
            R.id.radioButtonAmazing -> 0.20
            R.id.radioButtonGood -> 0.18
            else -> 0.15
        }

        var tip = tipPercentage * cost
        val roundUp = binding.roundUpSwitch.isChecked
        if (roundUp) tip = kotlin.math.ceil(tip)

        //Establece el resultado en el TextView
        binding.totalTextView.text = NumberFormat.getCurrencyInstance().format(tip).toString()
    }

    //Método que esconde teclado al presionar enter.
    private fun handleKeyEvent(keyCode: Int, editText: EditText): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            hideKeyBoard(editText)
            showTip(editText)
            return true
        }
        return false
    }

    //Método que esconde teclado
    private fun hideKeyBoard(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}