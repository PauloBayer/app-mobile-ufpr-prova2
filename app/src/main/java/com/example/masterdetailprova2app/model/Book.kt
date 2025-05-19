package com.example.masterdetailprova2app.model

data class Book(
    var id:     Int    = 0,
    var title:  String = "",
    var author: String = "",
    var pages:  Int    = 0
) {
    override fun toString() = title
}