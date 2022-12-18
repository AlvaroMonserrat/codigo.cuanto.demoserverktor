package codigo.cuanto

import io.ktor.server.application.*
import codigo.cuanto.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureRouting()

}

fun Application.bookModule(){
    book()
}

fun Application.userModule(){
    user()
}