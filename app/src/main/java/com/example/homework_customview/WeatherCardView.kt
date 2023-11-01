package com.example.homework_customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.example.homework_customview.databinding.WeatherCardBinding

class WeatherCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: WeatherCardBinding =
        WeatherCardBinding.inflate(LayoutInflater.from(context), this, true)

    var cityName: String = ""
        set(value) {
            field = value
            updateLocation()
        }

    var country: String = ""
        set(value) {
            field = value
            updateLocation()
        }

    var temperature: String = ""
        set(value) {
            field = value
            binding.tvTemperature.text = value
        }

    var timeOfDay: Int = 0
        set(value) {
            field = value
            updateWeatherIcon()
        }

    var precipitationType: Int = 0
        set(value) {
            field = value
            updateWeatherConditions()
            updateWeatherIcon()
        }

    var hasWind: Boolean = false
        set(value) {
            field = value
            updateWeatherConditions()
            updateWeatherIcon()
        }

    init {
        setAttrs(attrs, R.styleable.WeatherCardView) {
            cityName = it.getString(R.styleable.WeatherCardView_city_name) ?: ""
            country = it.getString(R.styleable.WeatherCardView_country) ?: ""
            temperature = it.getString(R.styleable.WeatherCardView_temperature) ?: ""
            timeOfDay = it.getInt(R.styleable.WeatherCardView_timeOfDay, 0)
            precipitationType = it.getInt(R.styleable.WeatherCardView_precipitationType, 0)
            hasWind = it.getBoolean(R.styleable.WeatherCardView_hasWind, false)
        }
    }

    private fun updateLocation() {
        binding.tvLocation.text = "$cityName, $country"
    }

    private fun updateWeatherConditions() {
        val weatherConditions = mutableListOf<String>().apply {
            when (precipitationType) {
                0 -> add(context.getString(R.string.light_rain))
                1 -> add(context.getString(R.string.heavy_rain))
                2 -> add(context.getString(R.string.light_snow))
                3 -> add(context.getString(R.string.tornado))
            }
            if (hasWind) add(context.getString(R.string.wind))
        }
        binding.tvWeatherCondition.text = weatherConditions.joinToString(", ")
    }

    private fun updateWeatherIcon() {
        binding.ivWeatherIcon.setImageResource(
            when {
                precipitationType == 3 -> R.drawable.ic_tornado
                timeOfDay == 1 && hasWind -> R.drawable.ic_moon_cloud_fast_wind
                timeOfDay == 1 && precipitationType == 0 -> R.drawable.ic_moon_cloud_mid_rain
                timeOfDay == 0 && precipitationType == 1 -> R.drawable.ic_sun_cloud_angled_rain
                else -> R.drawable.ic_sun_cloud_angled_rain
            }
        )
    }
}

inline fun View.setAttrs(
    attrs: AttributeSet?,
    styleable: IntArray,
    crossinline body: (TypedArray) -> Unit
) {
    context.theme.obtainStyledAttributes(attrs, styleable, 0, 0)
        .apply {
            try {
                body.invoke(this)
            } finally {
                recycle()
            }
        }
}