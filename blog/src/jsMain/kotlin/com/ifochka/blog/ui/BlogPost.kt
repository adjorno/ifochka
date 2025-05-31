package com.ifochka.blog.ui

import androidx.compose.runtime.*
import com.ifochka.blog.api.DevToApi
import com.ifochka.blog.model.DevToArticle
import org.jetbrains.compose.web.dom.*

@Composable
fun BlogPost(articleId: Int) {
    var article by remember { mutableStateOf<DevToArticle?>(null) }

    LaunchedEffect(articleId) {
        article = DevToApi.getArticleById(articleId)
    }

    article?.let {
        H1 { Text(it.title) }

        Article {
            Div(attrs = {
                attr("dangerouslySetInnerHTML", it.body_html ?: "<p>No content</p>")
            })
        }
    } ?: P { Text("Loading...") }
}