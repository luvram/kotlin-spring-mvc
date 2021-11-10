package me.luvram.kotlinspringmvc

import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import java.util.concurrent.CompletionStage

@Aspect
@Component
class MicrometerAspect(
    val meterRegistry: MeterRegistry
) {
    private val errorRate = ErrorRate()
    val gauge = Gauge.builder("error_rate", errorRate, ErrorRate::value).register(meterRegistry)

    @Around("execution(* me.luvram.kotlinspringmvc.UserController.save(..))")
    fun interceptAndRecord(pjp: ProceedingJoinPoint) : Any? {
        val method = (pjp.signature as MethodSignature).method
        val stopWhenCompleted = CompletionStage::class.java.isAssignableFrom(method.returnType)

        if (stopWhenCompleted) {
            return try {
                (pjp.proceed() as CompletionStage<*>)
                    .whenComplete { _, _ -> errorRate.value = 100.0 }
            } catch (e: Throwable) {
                errorRate.value = 0.0
                throw e
            }
        }

        return try {
            val result = pjp.proceed()
            errorRate.value = 100.0
            result
        } catch (e: Throwable) {
            errorRate.value = 0.0
            throw e
        }

    }

    class ErrorRate(
        var value: Double = 0.0
    )
}

