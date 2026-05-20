package com.example.nailytics

import android.graphics.Color

//Keeps track and updates all of the different analytes and their values and colors.
object AnalyteColorChartManager {

    private val analyteColorCharts: Map<Int, MutableList<ColorChartValue>> = mapOf(

        R.id.item_saliva_ph to mutableListOf(
            ColorChartValue("pH 5", 0xC9A70E3D.toInt()), //hexColor to Int
            ColorChartValue("pH 6", 0xD1811537.toInt()),
            ColorChartValue("pH 7", 0xD1621340.toInt()),
            ColorChartValue("pH 8", 0xDB366877.toInt())
        ),

        R.id.item_blood_ph to mutableListOf(
            ColorChartValue("pH 5", 0xC9A70E3D.toInt()), //hexColor to Int
            ColorChartValue("pH 6", 0xD1811537.toInt()),
            ColorChartValue("pH 7", 0xD1621340.toInt()),
            ColorChartValue("pH 8", 0xDB366877.toInt())
        ),

        R.id.item_urine_ph to mutableListOf(
            ColorChartValue("pH 5", 0xC9A70E3D.toInt()), //hexColor to Int
            ColorChartValue("pH 6", 0xD1811537.toInt()),
            ColorChartValue("pH 7", 0xD1621340.toInt()),
            ColorChartValue("pH 8", 0xDB366877.toInt())
        ),

        R.id.item_blood_glucose to mutableListOf(
            ColorChartValue("Glucose 40 mM", Color.rgb(164, 219, 149)),
            ColorChartValue("Glucose 80 mM", Color.rgb(149, 221, 135)),
            ColorChartValue("Glucose 120 mM", Color.rgb	(131, 211, 151)),
            ColorChartValue("Glucose 160 mM", Color.rgb(75, 198, 170))
        ),

        //OLD COLORS
        R.id.item_urine_glucose to mutableListOf(
            ColorChartValue("Glucose 40 mM", Color.rgb(193,220,37)),
            ColorChartValue("Glucose 80 mM", Color.rgb(167,200,15)),
            ColorChartValue("Glucose 120 mM", Color.rgb	(135,185,51)),
            ColorChartValue("Glucose 160 mM", Color.rgb(105,168,0))
        ),

        R.id.item_saliva_glucose to mutableListOf(
            ColorChartValue("Glucose 40 mM", Color.rgb(144, 206, 158)),
            ColorChartValue("Glucose 80 mM", Color.rgb(145, 216, 182)),
            ColorChartValue("Glucose 120 mM", Color.rgb	(133, 211, 181)),
            ColorChartValue("Glucose 160 mM", Color.rgb(96, 178, 148))
        ),

        R.id.item_blood_nitrate to mutableListOf(
            ColorChartValue("Nitrate 2.5 mM", Color.rgb(229, 181, 144)),
            ColorChartValue("Nitrate 5.0 mM", Color.rgb(224, 170, 166)),
            ColorChartValue("Nitrate 7.5 mM", Color.rgb(226, 142, 177)),
            ColorChartValue("Nitrate 10.0 mM", Color.rgb(214, 124, 161)),
        ),

        //OLD COLORS
        R.id.item_urine_nitrate to mutableListOf(
            ColorChartValue("Nitrate 2.5 mM", Color.rgb(255, 230, 80)),
            ColorChartValue("Nitrate 5.0 mM", Color.rgb(255, 220, 60)),
            ColorChartValue("Nitrate 7.5 mM", Color.rgb(245, 200, 60)),
            ColorChartValue("Nitrate 10.0 mM", Color.rgb(231,176,58))
        ),

        R.id.item_saliva_nitrate to mutableListOf(
            ColorChartValue("Nitrate 2.5 mM", Color.rgb(229, 181, 144)),
            ColorChartValue("Nitrate 5.0 mM", Color.rgb(224, 170, 166)),
            ColorChartValue("Nitrate 7.5 mM", Color.rgb(226, 142, 177)),
            ColorChartValue("Nitrate 10.0 mM", Color.rgb(214, 124, 161)),
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