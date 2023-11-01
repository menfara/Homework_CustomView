package com.example.homework_customview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.example.homework_customview.databinding.WeatherCardBinding

class WeatherCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: WeatherCardBinding =
        WeatherCardBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setAttrs(attrs, R.styleable.WeatherCardView) {
            binding.tvLocation.text = it.getString(R.styleable.WeatherCardView_city_name)
            binding.tvTemperature.text = it.getString(R.styleable.WeatherCardView_temperature)
            binding.tvWeatherCondition.text =
                it.getString(R.styleable.WeatherCardView_weather_description)

            binding.ivWeatherIcon.setImageResource(
                it.getResourceId(
                    R.styleable.WeatherCardView_weather_icon,
                    R.drawable.ic_moon_cloud_mid_rain
                )
            )


        }
    }

    var cityName: String
        get() = binding.tvLocation.text.toString()
        set(value) {
            binding.tvLocation.text = value
        }

    var temperature: String
        get() = binding.tvTemperature.text.toString()
        set(value) {
            binding.tvTemperature.text = value
        }

    var weatherDescription: String
        get() = binding.tvWeatherCondition.text.toString()
        set(value) {
            binding.tvWeatherCondition.text = value
        }

    var weatherIcon: Drawable?
        get() = binding.ivWeatherIcon.drawable
        set(value) {
            binding.ivWeatherIcon.setImageDrawable(value)
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

