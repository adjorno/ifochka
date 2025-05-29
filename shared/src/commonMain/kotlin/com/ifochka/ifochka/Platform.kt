package com.ifochka.ifochka

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform