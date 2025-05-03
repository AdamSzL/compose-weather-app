package com.example.weatherapp.core.domain

data class UiEvent<T>(
    val data: T,
    val onConsumed: () -> Unit
)

fun <T> T.asUiEvent(onConsumed: () -> Unit): UiEvent<T> =
    UiEvent(this, onConsumed)

fun <T> UiEvent<T>.consume() {
    onConsumed()
}
