package com.aptopayments.core.data.user

import java.io.Serializable
import java.util.Arrays
import java.util.HashMap

class DataPointList(dataPoints: List<DataPoint>? = null) : Serializable {

    private val dataPoints = HashMap<DataPoint.Type, MutableList<DataPoint>>()

    init {
        dataPoints?.let {
            for (dataPoint in it) {
                add(dataPoint)
            }
        }
    }

    fun add(dataPoint: DataPoint): DataPointList {
        val type = dataPoint.getType()
        val dataPointList = getDataPointsOf(type) ?: ArrayList()
        if (!dataPointList.contains(dataPoint)) {
            dataPointList.add(dataPoint)
        }
        dataPoints[type] = Arrays.asList<DataPoint>(dataPoint)
        return this
    }

    fun getDataPointsOf(type: DataPoint.Type): MutableList<DataPoint>? {
        return dataPoints[type]
    }

    fun getUniqueDataPointOf(type: DataPoint.Type, defaultValue: DataPoint?): DataPoint? {
        getDataPointsOf(type)?.let { return it.first() }
        return defaultValue
    }

    fun getAllDataPoints(): List<DataPoint>? {
        val allDataPoints = ArrayList<DataPoint>()
        dataPoints.forEach {
            allDataPoints.addAll(it.value)
        }
        return allDataPoints
    }

    fun isEmpty(): Boolean = dataPoints.isEmpty()
}
