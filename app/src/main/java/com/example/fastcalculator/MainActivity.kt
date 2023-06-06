package com.example.fastcalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.fastcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var operation = "" // переменная для хранения выбранной операции
    private var haveDot = false // переменная для хранения условия о наличии точки в числе
    private var haveFirstNumber = false // переменная для хранения условия о введении первого числа
    private var firstNumber = 0f // переменная для хранения первого числа
    private var secondNumber = 0f // переменная для хранения второго числа
    private var resultOperation: Float? = 0f // переменная для хранения результата

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
    }

    @SuppressLint("SetTextI18n")
    private fun initButtons() = with(binding){

        // функция для отслеживания ввода точки
        edInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                haveDot = "." in s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                return
            }
        })

        // функция для реализации вводна цифр при помощи кнопок
        btOne.setOnClickListener {
            edInput.setText(edInput.text.toString() + "1")
        }

        btTwo.setOnClickListener {
            edInput.setText(edInput.text.toString() + "2")
        }

        btThree.setOnClickListener {
            edInput.setText(edInput.text.toString() + "3")
        }

        btFour.setOnClickListener {
            edInput.setText(edInput.text.toString() + "4")
        }

        btFive.setOnClickListener {
            edInput.setText(edInput.text.toString() + "5")
        }

        btSix.setOnClickListener {
            edInput.setText(edInput.text.toString() + "6")
        }

        btSeven.setOnClickListener {
            edInput.setText(edInput.text.toString() + "7")
        }

        btEight.setOnClickListener {
            edInput.setText(edInput.text.toString() + "8")
        }

        btNine.setOnClickListener {
            edInput.setText(edInput.text.toString() + "9")
        }

        btZero.setOnClickListener {
            edInput.setText(edInput.text.toString() + "0")
        }

        // функция для ввода точки
        dot.setOnClickListener {
            if (!haveDot){
                edInput.setText(edInput.text.toString() + ".")
            }
        }

        // функция для стирания введенного числа
        AC.setOnClickListener {
            edInput.text.clear()
        }

        // функция для стирания последнего введенного символа
        clear.setOnClickListener {
            if (edInput.text.isNotEmpty()) {
                val text = edInput.text.toString()
                edInput.setText(text.substring(0, text.length - 1))
            }
        }

        // функция выбора операции деления
        sep.setOnClickListener {
            if ((operation == "") and (!haveFirstNumber) and edInput.text.isNotEmpty() and (edInput.text.toString() != "-")) {
                operation = "/"
                firstNumber = edInput.text.toString().toFloat()
                edInput.text.clear()
                haveFirstNumber = true
                tvLastOperation.text = "${firstNumber} ${operation}"
                haveDot = false
            }
        }

        mul.setOnClickListener {
            if ((operation == "") and (!haveFirstNumber) and edInput.text.isNotEmpty() and (edInput.text.toString() != "-")) {
                operation = "x"
                firstNumber = edInput.text.toString().toFloat()
                edInput.text.clear()
                haveFirstNumber = true
                tvLastOperation.text = "${firstNumber} ${operation}"
                haveDot = false
            }
        }

        minus.setOnClickListener {
            if (edInput.text.isEmpty() and (edInput.text.toString() != "-")) {
                edInput.setText(edInput.text.toString() + "-")
            } else {
                if ((operation == "") and (!haveFirstNumber) and (edInput.text.toString() != "-")) {
                    operation = "-"
                    firstNumber = edInput.text.toString().toFloat()
                    edInput.text.clear()
                    haveFirstNumber = true
                    tvLastOperation.text = "${firstNumber} ${operation}"
                    haveDot = false
                }
            }
        }

        plus.setOnClickListener {
            if ((operation == "") and (!haveFirstNumber) and edInput.text.isNotEmpty() and (edInput.text.toString() != "-")) {
                operation = "+"
                firstNumber = edInput.text.toString().toFloat()
                edInput.text.clear()
                haveFirstNumber = true
                tvLastOperation.text = "${firstNumber} ${operation}"
                haveDot = false
            }
        }

        // функция для подсчета результата
        res.setOnClickListener {
            if ((edInput.text.isNotEmpty()) and haveFirstNumber) {
                secondNumber = edInput.text.toString().toFloat()

                if (secondNumber == 0f){
                    Toast.makeText(
                        this@MainActivity,
                        "Деление на ноль невозможно",
                        Toast.LENGTH_LONG
                    ).show()

                    return@setOnClickListener
                }

                resultOperation = calcResult(
                    firstNumber,
                    edInput.text.toString().toFloat(),
                    operation
                )

                edInput.setText(resultOperation.toString())
                tvLastOperation.text = "${firstNumber} ${operation} ${secondNumber}"

                haveFirstNumber = false
                haveDot = false
                operation = ""
            }
        }
    }

    private fun calcResult(first: Float, second: Float, operation: String): Float?{
        when (operation){
            "+" -> return first + second
            "-" -> return first - second
            "/" -> return first / second
            "x" -> return first * second
        }

        return null
    }
}