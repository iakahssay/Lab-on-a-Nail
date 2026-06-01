package com.example.nailytics

import android.graphics.Color

//Keeps track and updates all of the different analytes and their values and colors.
object AnalyteColorChartManager {

    private val analyteColorCharts: Map<Int, MutableList<ColorChartValue>> = mapOf(

        //COLORS FROM LAB TESTS
        R.id.item_saliva_ph to mutableListOf(
            ColorChartValue("pH 5", Color.rgb(77, 64,	73)),
            ColorChartValue("pH 6", Color.rgb(64, 56, 62)),
            ColorChartValue("pH 7", Color.rgb(56, 53, 63)),
            ColorChartValue("pH 8", Color.rgb(57, 57, 62))
        ),

        //COLORS FROM LAB TESTS
        R.id.item_blood_ph to mutableListOf(
            ColorChartValue("pH 5", Color.rgb(77, 64,	73)),
            ColorChartValue("pH 6", Color.rgb(64, 56, 62)),
            ColorChartValue("pH 7", Color.rgb(56, 53, 63)),
            ColorChartValue("pH 8", Color.rgb(57, 57, 62))
        ),

        //NEW COLORS FROM RBG COLOR SWATCH UNDER NAILS ON PAPER
        R.id.item_urine_ph to mutableListOf(
            ColorChartValue("pH 5", Color.rgb(110, 61, 71)),
            ColorChartValue("pH 6", Color.rgb(91, 59, 70)),
            ColorChartValue("pH 7", Color.rgb(77, 60, 78)),
            ColorChartValue("pH 8", Color.rgb(46, 58, 78))
        ),

        //NEW COLORS FROM RBG COLOR SWATCH UNDER NAILS ON PAPER
        R.id.item_blood_glucose to mutableListOf(
            ColorChartValue("Glucose 40 mM", Color.rgb(114, 123, 105)),
            ColorChartValue("Glucose 80 mM", Color.rgb(97, 109, 111)),
            ColorChartValue("Glucose 120 mM", Color.rgb	(81, 99, 95)),
            ColorChartValue("Glucose 160 mM", Color.rgb(65, 72, 68))
        ),

        //NEW COLORS FROM RBG COLOR SWATCH UNDER NAILS ON PAPER
        R.id.item_urine_glucose to mutableListOf(
            ColorChartValue("Glucose 40 mM", Color.rgb(114, 123, 105)),
            ColorChartValue("Glucose 80 mM", Color.rgb(97, 109, 111)),
            ColorChartValue("Glucose 120 mM", Color.rgb	(81, 99, 95)),
            ColorChartValue("Glucose 160 mM", Color.rgb(65, 72, 68))
        ),

        //NEW COLORS FROM RBG COLOR SWATCH UNDER NAILS ON PAPER
        R.id.item_saliva_glucose to mutableListOf(
            ColorChartValue("Glucose 40 mM", Color.rgb(114, 123, 105)),
            ColorChartValue("Glucose 80 mM", Color.rgb(97, 109, 111)),
            ColorChartValue("Glucose 120 mM", Color.rgb	(81, 99, 95)),
            ColorChartValue("Glucose 160 mM", Color.rgb(65, 72, 68))
        ),

        //NEW COLORS FROM RBG COLOR SWATCH UNDER NAILS ON PAPER
        R.id.item_blood_nitrate to mutableListOf(
            ColorChartValue("Nitrate 2.5 mM", Color.rgb(212, 175, 171)),
            ColorChartValue("Nitrate 5.0 mM", Color.rgb(224, 170, 166)),
            ColorChartValue("Nitrate 7.5 mM", Color.rgb(208, 116, 155)),
            ColorChartValue("Nitrate 10.0 mM", Color.rgb(197, 95, 124)),
        ),

        //NEW COLORS FROM RBG COLOR SWATCH UNDER NAILS ON PAPER
        R.id.item_urine_nitrate to mutableListOf(
            ColorChartValue("Nitrate 2.5 mM", Color.rgb(212, 175, 171)),
            ColorChartValue("Nitrate 5.0 mM", Color.rgb(224, 170, 166)),
            ColorChartValue("Nitrate 7.5 mM", Color.rgb(208, 116, 155)),
            ColorChartValue("Nitrate 10.0 mM", Color.rgb(197, 95, 124)),
        ),

        //NEW COLORS FROM RBG COLOR SWATCH UNDER NAILS ON PAPER
        R.id.item_saliva_nitrate to mutableListOf(
            ColorChartValue("Nitrate 2.5 mM", Color.rgb(212, 175, 171)),
            ColorChartValue("Nitrate 5.0 mM", Color.rgb(224, 170, 166)),
            ColorChartValue("Nitrate 7.5 mM", Color.rgb(208, 116, 155)),
            ColorChartValue("Nitrate 10.0 mM", Color.rgb(197, 95, 124)),
        )
    )

    fun getChart(analyteId: Int): List<ColorChartValue> {
        return analyteColorCharts[analyteId] ?: emptyList()
    }

    fun updateColor(analyteId: Int, label: String, newColor: Int) {
        analyteColorCharts[analyteId]
            ?.find { it.label == label }
            ?.color = newColor
    }
}