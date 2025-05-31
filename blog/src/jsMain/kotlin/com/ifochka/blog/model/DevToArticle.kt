package com.ifochka.blog.model

import kotlinx.serialization.Serializable

@Serializable
data class DevToArticle(
    val id: Int,
    val title: String,
    val slug: String,
    val url: String,
    val body_html: String? = null
)