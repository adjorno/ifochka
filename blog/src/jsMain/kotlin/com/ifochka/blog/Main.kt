package com.ifochka.blog

import com.ifochka.blog.ui.BlogList
import com.ifochka.blog.ui.BlogPost
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

fun main() {
    val path = window.location.pathname.removeSuffix("/").removePrefix("/")

    println("🔁 Path: $path")

    renderComposable(rootElementId = "root") {
        when {
            path.isEmpty() -> BlogList()
            path.toIntOrNull() != null -> BlogPost(articleId = path.toInt())
            else -> H1 { Text("404 - Not Found") }
        }
    }
}
