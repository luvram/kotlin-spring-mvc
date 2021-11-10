package me.luvram.kotlinspringmvc

import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {
    companion object {
        val log = LoggerFactory.getLogger(javaClass)
    }

    @PostMapping
    @Timed
    fun save(@RequestBody userRequest: UserRequest) {
        println("here")
        userService.save(userRequest)
    }

}
