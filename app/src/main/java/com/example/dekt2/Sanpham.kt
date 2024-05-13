package com.example.dekt2

import com.google.firebase.firestore.Exclude

data class Sanpham (
    @Exclude var IDsp: String? = "",
    var tensp: String = "",
    var loaisp: String = "",
    var giasp: Int = 0,
    var urlsp: String? = ""
)