package com.ifochka.blog

import androidx.compose.runtime.*
import com.ifochka.blog.ui.BlogList
import com.ifochka.blog.ui.BlogPost
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

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
    val base = (document.querySelector("base")?.getAttribute("href") ?: "/")
        .trim('/')                      // e.g. "blog"
    val full = window.location.pathname.trim('/')  // e.g. "blog/1234"

    val relative = if (base.isNotEmpty() && full.startsWith(base))
        full.drop(base.length).trimStart('/')      // "1234"
    else
        full                                       // dev path

    return relative.ifBlank { null }               // "" → list page
}
