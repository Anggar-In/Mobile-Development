package com.example.anggarin.data.dummyKategori

data class Category(val id: Int, val name: String)

val dummyCategories = listOf(
    Category(1, "🍕 Makanan"),
    Category(2, "🚗 Transportasi"),
    Category(3, "🎢 Hiburan"),
)