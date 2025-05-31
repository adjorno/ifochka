package com.ifochka.blog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.ifochka.blog.ui.BlogList
import com.ifochka.blog.ui.BlogPost
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        val path = window.location.pathname
        println("🔁 Path: $path")
        Router { slug ->
            println("🔁 Slug: $slug")
            if (slug == null) BlogList() else BlogPost(slug)
        }
    }
}

@Composable
fun Router(content: @Composable (String?) -> Unit) {
    val location = remember { mutableStateOf(currentPath()) }

    LaunchedEffect(Unit) {
        window.onpopstate = { location.value = currentPath() }
    }

    content(location.value)
}

private fun currentPath(): String? {
    val base = (document.querySelector("base")?.getAttribute("href") ?: "/")
        .trim('/')                      // e.g. "blog"
    val full = window.location.pathname.trim('/')  // e.g. "blog/1234"

    val relative = if (base.isNotEmpty() && full.startsWith(base))
        full.drop(base.length).trimStart('/')      // "1234"
    else
        full                                       // dev path

    return relative.ifBlank { null }               // "" → list page
}
