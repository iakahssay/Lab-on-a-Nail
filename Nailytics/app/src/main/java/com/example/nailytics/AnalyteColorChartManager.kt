package com.example.nailytics

import android.graphics.Color

//Keeps track and updates all of the different analytes and their values and colors.
object AnalyteColorChartManager {

    private val analyteColorCharts: Map<Int, MutableList<ColorChartValue>> = mapOf(

        //NEW COLORS FROM RBG COLOR SWATCH UNDER NAILS ON PAPER
        R.id.item_saliva_ph to mutableListOf(
            ColorChartValue("pH 5", Color.rgb(110, 61, 71)),
            ColorChartValue("pH 6", Color.rgb(91, 59, 70)),
            ColorChartValue("pH 7", Color.rgb(77, 60, 78)),
            ColorChartValue("pH 8", Color.rgb(46, 58, 78))
        ),

        //NEW COLORS FROM RBG COLOR SWATCH UNDER NAILS ON PAPER
        R.id.item_blood_ph to mutableListOf(
            ColorChartValue("pH 5", Color.rgb(110, 61, 71)),
            ColorChartValue("pH 6", Color.rgb(91, 59, 70)),
            ColorChartValue("pH 7", Color.rgb(77, 60, 78)),
            ColorChartValue("pH 8", Color.rgb(46, 58, 78))
        ),

        //OLD COLORS FROM NAILS ON PAPER
        R.id.item_urine_ph to mutableListOf(
            ColorChartValue("pH 5", 0xC9A70E3D.toInt()), //hexColor to Int
            ColorChartValue("pH 6", 0xD1811537.toInt()),
            ColorChartValue("pH 7", 0xD1621340.toInt()),
            ColorChartValue("pH 8", 0xDB366877.toInt())
        ),

        //NEW COLORS FROM RBG COLOR SWATCH UNDER NAILS ON PAPER
        R.id.item_blood_glucose to mutableListOf(
            ColorChartValue("Glucose 40 mM", Color.rgb(114, 123, 105)),
            ColorChartValue("Glucose 80 mM", Color.rgb(97, 109, 111)),
            ColorChartValue("Glucose 120 mM", Color.rgb	(81, 99, 95)),
            ColorChartValue("Glucose 160 mM", Color.rgb(65, 72, 68))
        ),

        //OLD COLORS FROM NAILS ON PAPER
        R.id.item_urine_glucose to mutableListOf(
            ColorChartValue("Glucose 40 mM", Color.rgb(149, 221, 135)),
            ColorChartValue("Glucose 80 mM", Color.rgb(131, 211, 151)),
            ColorChartValue("Glucose 120 mM", Color.rgb(103, 205, 160)),
            ColorChartValue("Glucose 160 mM", Color.rgb(75, 198, 170))
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

        //OLD COLORS FROM NAILS ON PAPER
        R.id.item_urine_nitrate to mutableListOf(
            ColorChartValue("Nitrate 2.5 mM", Color.rgb(229, 181, 144)),
            ColorChartValue("Nitrate 5.0 mM", Color.rgb(224, 170, 166)),
            ColorChartValue("Nitrate 7.5 mM", Color.rgb(226, 142, 177)),
            ColorChartValue("Nitrate 10.0 mM", Color.rgb(214, 124, 161)),
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