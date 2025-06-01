package com.ifochka.blog.ui

import androidx.compose.runtime.*
import com.ifochka.blog.api.DevToApi
import com.ifochka.blog.model.DevToArticleSummary
import org.jetbrains.compose.web.dom.*

@Composable
fun BlogList() {
    var articles by remember { mutableStateOf<List<DevToArticleSummary>>(emptyList()) }

    LaunchedEffect(Unit) {
        articles = DevToApi.getArticles()
    }

    H1 { Text("My Blog") }

    Ul {
        println("Articles: ${articles.joinToString { it.title }}")
        articles.forEach { article ->
            Li {
                A(href = "/blog/${article.id}") {
                    Text(article.title)
                }
            }
        }
    }
}