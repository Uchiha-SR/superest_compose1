package dev.android.firebasetest

sealed class DataState {
    class Success(val data: MutableList<Products>) : DataState()
    class Failure(val message: String) : DataState()
    object Loading : DataState()
    object Empty : DataState()
}
