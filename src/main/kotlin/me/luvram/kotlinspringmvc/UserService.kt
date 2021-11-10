package me.luvram.kotlinspringmvc

import com.fasterxml.jackson.databind.ObjectMapper
import org.redisson.api.RedissonClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.security.InvalidParameterException
import java.util.UUID

@Service
class UserService(
    private val redissonClient: RedissonClient,
    private val objectMapper: ObjectMapper,
    private val userRepository: UserRepository,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    fun save(userRequest: UserRequest) {
        if (userRequest.age < 0) {
            throw InvalidParameterException()
        }
        val map =
            redissonClient.getMap<String, User>("user", TypedJsonJacksonCodec(String::class.java, User::class.java, objectMapper))
        val user = User(
            UUID.randomUUID().toString(),
            userRequest.name,
            userRequest.age
        )
        map[user.id] = user
        userRepository.save(user)
        kafkaTemplate.send("tmp_topic", user.name)
    }

}
