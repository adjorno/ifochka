package com.ifochka.blog

import androidx.compose.runtime.*
import com.ifochka.blog.ui.BlogList
import com.ifochka.blog.ui.BlogPost
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.url.URL

@JsName("commitSha")
external val commitSha: String

fun main() {
    renderComposable(rootElementId = "root") {
        println("🔁 commitSha: $commitSha")
        val path = window.location.pathname
        println("🔁 Path: $path")

        Div {
            Router { slug ->
                println("🔁 Slug: $slug")
                if (slug == null) BlogList() else BlogPost(slug)
            }

            Footer {
                Hr()
                Small {
                    Text("Version: $commitSha")
                }
            }
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
    val basePath = URL(window.document.baseURI).pathname.trim('/')   // ← change
    val full     = window.location.pathname.trim('/')

    val relative = if (basePath.isNotEmpty() && full.startsWith(basePath))
        full.drop(basePath.length).trimStart('/')
    else
        full

    return relative.ifBlank { null }
}
