package com.ifochka.blog.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DevToArticle(
    val id: Int,
    val title: String,
    val slug: String,
    val url: String,
    @SerialName("body_html")
    val bodyHtml: String? = null,
    @SerialName("body_markdown")
    val bodyMarkdown: String? = null,
)