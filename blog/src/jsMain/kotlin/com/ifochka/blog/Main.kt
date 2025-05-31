package com.ifochka.blog

import com.ifochka.blog.ui.BlogList
import com.ifochka.blog.ui.BlogPost
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import kotlinx.browser.window

fun main() {
    renderComposable(rootElementId = "root") {
        val path = window.location.pathname

        when {
            path == "/blog" || path == "/blog/" -> BlogList()
            path.startsWith("/blog/") -> {
                val id = path.removePrefix("/blog/").toIntOrNull()
                if (id != null) BlogPost(articleId = id)
                else H1 { Text("Invalid blog ID") }
            }
            else -> H1 { Text("404 - Not Found") }
        }
    }
}
