package com.ifochka.blog

import com.ifochka.blog.ui.BlogList
import com.ifochka.blog.ui.BlogPost
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

@OptIn(ExperimentalJsExport::class)
@JsExport
@JsName("commitSha")
val commitSha: String = "dev-local"

fun main() {
    val fullPath = window.location.pathname.removeSuffix("/")
    println("Path: $fullPath")
    val slug = fullPath.removePrefix("/blog").removePrefix("/")
    println("Slug: $slug")

    renderComposable(rootElementId = "root") {
        when {
            slug.isNotBlank() -> BlogPost(articleId = slug)
            else -> BlogList()
        }

        Footer {
            Hr()
            Small { Text("Version: $commitSha") }
        }
    }
}
