package com.ifochka.blog

import com.ifochka.blog.ui.BlogList
import com.ifochka.blog.ui.BlogPost
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

fun main() {
    val search = window.location.search.removePrefix("?")
    println("🔍 location.search = '${window.location.search}'")
    println("🔍 search (cleaned) = '$search'")

    val pathFromSearch = if (search.startsWith("/")) {
        val result = search.removePrefix("/")
        println("✅ Detected redirect path from search: '$result'")
        result
    } else {
        println("ℹ️ No redirect path in search query")
        null
    }

    val pathname = window.location.pathname
    val slug = when {
        pathFromSearch != null -> pathFromSearch // e.g. "2542356" from ?/2542356
        else -> {
            val result = pathname.removePrefix("/blog/").removeSuffix("/")
            println("🔁 Fallback to pathname: '$pathname' → Slug = '$result'")
            result
        }
    }

    println("📦 Final slug to use: '$slug'")


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
