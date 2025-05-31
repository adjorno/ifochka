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
        val path = window.location.pathname.removeSuffix("/").removePrefix("/")
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
    val base = document.querySelector("base")?.getAttribute("href") ?: "/"
    val full = window.location.pathname
    val relative = if (full.startsWith(base)) full.removePrefix(base) else full.removePrefix("/")
    return relative.ifBlank { null }           // null → index page
}