package com.ifochka.blog.devto

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.java.Java
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class DevToClient {
    private val client = HttpClient(Java) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun getArticles(): List<DevToArticle> {
        return try {
            val response: HttpResponse = client.get("https://dev.to/api/articles?username=ifochka")
            println("Dev.to status: ${response.status}")
            response.body()
        } catch (e: Exception) {
            println("Error fetching articles: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }
}

@Serializable
data class DevToArticle(
    val id: Int,
    val title: String,
    val slug: String,
    val url: String
)
