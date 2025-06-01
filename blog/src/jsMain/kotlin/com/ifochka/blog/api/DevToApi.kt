package com.ifochka.blog.api

import com.ifochka.blog.model.DevToArticleFull
import com.ifochka.blog.model.DevToArticleSummary
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object DevToApi {
    private val json = Json { ignoreUnknownKeys = true }
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(json)
        }
    }

    suspend fun getArticles(): List<DevToArticleSummary> =
        client.get("https://dev.to/api/articles?username=ifochka").body()


    suspend fun getArticleById(id: String): DevToArticleFull =
        client.get("https://dev.to/api/articles/$id").body()
}
