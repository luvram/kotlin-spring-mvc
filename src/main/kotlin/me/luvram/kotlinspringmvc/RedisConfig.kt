package me.luvram.kotlinspringmvc

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.client.codec.StringCodec
import org.redisson.config.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedisConfig {
    @Bean
    fun redissonClient(): RedissonClient {
        return Redisson.create(Config().apply {
            this.useSingleServer().address = "redis://localhost:6380"
            this.codec = StringCodec()
        })
    }
}
