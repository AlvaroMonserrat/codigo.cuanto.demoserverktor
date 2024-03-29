package codigo.cuanto.plugins

import codigo.cuanto.model.Person
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}

fun Application.user(){
    install(ContentNegotiation){
        json()
    }
    install(CallLogging)
    routing {
        get("/users/{username}") {
            val username = call.parameters["username"]
            val header = call.request.headers["Connection"]
            if (username == "Admin"){
                call.response.header(name = "CustomHeader", "Admin")
                call.respond(message = "Hello Admin", status = HttpStatusCode.OK)
            }
            call.respondText("Greetings, $username with header: $header ")
        }
        //http://127.0.0.1:8080/user?name=alvaro&age=31
        get("/user"){
            val name = call.request.queryParameters["name"]
            val age = call.request.queryParameters["age"]
            call.respondText("Hi, my name is $name and my age is $age")
        }

        get("/person"){
            try {
                val person = Person(name="John", age = 25)
                call.respond(message=person, status = HttpStatusCode.OK)
            }catch (e: Exception){
                call.respond(message=e.message.toString(), status = HttpStatusCode.BadRequest)
            }
        }
        get("/redirect"){
            call.respondRedirect(url="/moved", permanent = false)
        }
        get("/moved"){
            call.respondText("You have been successfully redirected!")
        }
        static {
            resource("index.html")
        }
        get("/home"){
            call.respondHtml {
                head {
                    title{
                        +"Hi"
                    }
                }
                body {
                    h1 {
                        +"Welcome to this page"
                    }
                    p{ +"Current directory is: ${System.getProperty("user.dir")}" }
                }
            }
        }
    }
}

fun Application.book() {

    routing {
        get("/book") {
            call.respondText("Lord of the Ring")
        }
    }
}