package com.aptopayments.mobile.data.user

import java.io.Serializable
import java.util.HashMap

data class DataPointList(val dataPoints: List<DataPoint>? = null) : Serializable {

    private val dataPointsHash = HashMap<DataPoint.Type, MutableList<DataPoint>>()

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
        dataPointsHash[type] = mutableListOf(dataPoint)
        return this
    }

    fun getDataPointsOf(type: DataPoint.Type): MutableList<DataPoint>? {
        return dataPointsHash[type]
    }

    fun getUniqueDataPointOf(type: DataPoint.Type, defaultValue: DataPoint?): DataPoint? {
        getDataPointsOf(type)?.let { return it.first() }
        return defaultValue
    }

    fun getAllDataPoints(): List<DataPoint>? {
        val allDataPoints = ArrayList<DataPoint>()
        dataPointsHash.forEach {
            allDataPoints.addAll(it.value)
        }
        return allDataPoints
    }

    fun isEmpty(): Boolean = dataPointsHash.isEmpty()
}
