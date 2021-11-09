package me.luvram.kotlinspringmvc

import io.micrometer.core.aop.TimedAspect
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

@EnableAspectJAutoProxy
@ConditionalOnClass(MeterRegistry::class)
@Configuration
class MicrometerConfig {
    @Bean
    fun timedAspect(registry: MeterRegistry): TimedAspect =
        TimedAspect(registry)
}
