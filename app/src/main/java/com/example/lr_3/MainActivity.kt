package com.example.lr_3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val solarCalculator = SolarCalculator()

        val resultOutput: TextView = findViewById(R.id.resultOutput)

        val energyPowerInput: EditText = findViewById(R.id.energyPowerInput)
        val oldErrorDeviationInput: EditText = findViewById(R.id.oldErrorDeviationInput)
        val newErrorDeviationInput: EditText = findViewById(R.id.newErrorDeviationInput)
        val energyPriceInput: EditText = findViewById(R.id.energyPriceInput)

        val calcButton: Button = findViewById(R.id.calcButton)

        calcButton.setOnClickListener  {
            val energyPower = energyPowerInput.text.toString().toDoubleOrNull() ?: 0.0
            val oldErrorDeviation = oldErrorDeviationInput.text.toString().toDoubleOrNull() ?: 0.0
            val newErrorDeviation = newErrorDeviationInput.text.toString().toDoubleOrNull() ?: 0.0
            val energyPrice = energyPriceInput.text.toString().toDoubleOrNull() ?: 0.0

            val calcProbabilityImbalance = solarCalculator.calculateProbability(
                lowerBound = energyPower - newErrorDeviation,
                upperBound = energyPower + newErrorDeviation
            )

            val profitImbalance = solarCalculator.calculateProfit(
                averagePower = energyPower,
                probability = calcProbabilityImbalance / 100,
                energyPrice = energyPrice
            )

            val peniyaImbalance = solarCalculator.calculateProfit(
                averagePower = energyPower,
                probability = (100 - calcProbabilityImbalance) / 100,
                energyPrice = energyPrice
            )


            val calcProbabilityBalance = solarCalculator.calculateProbability(
                lowerBound = energyPower - oldErrorDeviation,
                upperBound = energyPower + oldErrorDeviation
            )

            val profitBalance = solarCalculator.calculateProfit(
                averagePower = energyPower,
                probability = calcProbabilityBalance / 100,
                energyPrice = energyPrice
            )

            val peniyaBalance = solarCalculator.calculateProfit(
                averagePower = energyPower,
                probability = (100 - calcProbabilityBalance) / 100,
                energyPrice = energyPrice
            )

            resultOutput.text = "Probability Imbalance: $calcProbabilityImbalance%\n" +
                                "Profit Imbalance: $profitImbalance\n" +
                                "Peniya Imbalance: $peniyaImbalance\n" +
                                "Total Imbalance: ${profitImbalance - peniyaImbalance}\n\n" +
                                "Probability Balance: $calcProbabilityBalance%\n" +
                                "Profit Balance: $profitBalance\n" +
                                "Peniya Balance: $peniyaBalance\n" +
                                "Total Balance: ${profitBalance - peniyaBalance}"
        }
    }
}