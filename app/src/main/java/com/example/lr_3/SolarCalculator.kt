package com.example.lr_3

import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sqrt

class SolarCalculator {
    fun calculateProfit(
        averagePower: Double,
        probability: Double,
        energyPrice: Double,
    ): Double {
        return (averagePower * 24 * probability) * energyPrice
    }

    private fun normalDistribution(x: Double, mean: Double = 5.0, stdDev: Double = 1.0): Double {
        return (1 / (stdDev * sqrt(2 * PI))) * exp(-((x - mean) * (x - mean)) / (2 * stdDev * stdDev))
    }

    fun calculateProbability(lowerBound: Double, upperBound: Double, step: Double = 0.001): Double {
        var integral = 0.0
        var x = lowerBound

        while (x < upperBound) {
            val y1 = normalDistribution(x)
            val y2 = normalDistribution(x + step)
            integral += (y1 + y2) * step / 2
            x += step
        }

        return integral * 100
    }
}