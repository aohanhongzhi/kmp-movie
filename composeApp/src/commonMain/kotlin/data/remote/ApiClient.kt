package data.remote

import io.ktor.client.*
import io.ktor.client.engine.ProxyBuilder
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import utils.AppConstant

val client = HttpClient {
    defaultRequest {
        url {
            takeFrom(AppConstant.BASE_URL)
            parameters.append("api_key", AppConstant.API_KEY)
        }
    }
    expectSuccess = true
    install(HttpTimeout) {
        val timeout = 30000L
        connectTimeoutMillis = timeout
        requestTimeoutMillis = timeout
        socketTimeoutMillis = timeout
    }
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.HEADERS
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
    }
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
    engine {
        proxy = ProxyBuilder.socks("127.0.0.1", 20170)
    }
}