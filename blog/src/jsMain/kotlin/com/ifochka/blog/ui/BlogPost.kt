package com.ifochka.blog.ui

import androidx.compose.runtime.*
import com.ifochka.blog.api.DevToApi
import com.ifochka.blog.model.DevToArticleFull
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Article
import org.w3c.dom.HTMLElement

@Composable
fun BlogPost(articleId: String) {
    var article by remember { mutableStateOf<DevToArticleFull?>(null) }
    val htmlContainer = remember { mutableStateOf<HTMLElement?>(null) }

    LaunchedEffect(articleId) {
        article = DevToApi.getArticleById(articleId)
    }

    article?.let { article ->
        H1 { Text(article.title) }

        Article {
            Div(attrs = {
                ref {
                    htmlContainer.value = it
                    onDispose { htmlContainer.value = null }
                }
            })
        }

        // This effect injects HTML into the div after composition
        LaunchedEffect(article.bodyHtml) {
            htmlContainer.value?.innerHTML = article.bodyHtml ?: "<p>No content</p>"
        }
    } ?: Text("Loading...")
}
