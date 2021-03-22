package com.example.hamsterone.models

import java.io.Serializable

data class MyPhoto(
    val id: String,
    val url: String,
    val type: String
) : Serializable