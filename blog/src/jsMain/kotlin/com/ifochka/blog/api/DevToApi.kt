package com.ifochka.blog.api

import com.ifochka.blog.model.DevToArticle
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object DevToApi {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun getArticles(): List<DevToArticle> =
        client.get("https://dev.to/api/articles?username=ifochka").body()

    suspend fun getArticleById(id: String): DevToArticle =
        client.get("https://dev.to/api/articles/$id").body()
}
