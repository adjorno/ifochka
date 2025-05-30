package com.ifochka.blog

import com.ifochka.blog.devto.DevToClient
import io.ktor.http.HttpStatusCode
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.html.*
import kotlinx.html.*

fun main() {
    println("Starting server...")
    embeddedServer(Netty, port = 8080) {
        blogRoutes()
    }.start(wait = true)
}

fun Application.blogRoutes() {
    val devToClient = DevToClient()

    routing {
        get("/blog") {
            val articles = devToClient.getArticles() // ✅ now runs safely inside suspend block
            call.respondHtml {
                head { title { +"My Blog" } }
                body {
                    h1 { +"Articles from Dev.to" }
                    ul {
                        for (article in articles) {
                            li {
                                a(href = "/blog/${article.slug}") {
                                    +article.title
                                }
                            }
                        }
                    }
                }
            }
        }

        get("/blog/{slug}") {
            val slug = call.parameters["slug"]
            if (slug.isNullOrBlank()) {
                return@get call.respondText("Missing slug", status = HttpStatusCode.BadRequest)
            }

            call.respondHtml {
                head {
                    title { +"Blog Post - $slug" }
                    style {
                        +"iframe { border: none; width: 100%; height: 100vh; }"
                    }
                }
                body {
                    iframe {
                        src = "https://dev.to/ifochka/$slug"
                    }
                }
            }
        }
    }
}
