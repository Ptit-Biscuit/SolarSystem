import org.openrndr.math.Vector2
import kotlin.math.cos

enum class Movement(val pattern: (Enemy, Double, Double) -> Unit) {
    NONE({ e, worldSpeed, deltaTime ->
        e.pos += Vector2.UNIT_Y * worldSpeed * deltaTime
    }),
    STRAIGHT_SLOW({ e, worldSpeed, deltaTime ->
        e.pos += Vector2(.0, .8) * worldSpeed * deltaTime
    }),
    STRAIGHT_FAST({ e, worldSpeed, deltaTime ->
        e.pos += Vector2(.0, 1.4) * worldSpeed * deltaTime
    }),
    SINUSOID_SLOW_NARROW({ e, worldSpeed, deltaTime ->
        e.dataMove[0] += deltaTime
        e.pos += Vector2(50.0f * cos(e.dataMove[0]) * deltaTime, .5 * worldSpeed * deltaTime)
    }),
    SINUSOID_SLOW_WIDE({ e, worldSpeed, deltaTime ->
        e.dataMove[0] += deltaTime
        e.pos += Vector2(250.0f * cos(e.dataMove[0]) * deltaTime, .5 * worldSpeed * deltaTime)
    }),
    SINUSOID_NARROW({ e, worldSpeed, deltaTime ->
        e.dataMove[0] += deltaTime
        e.pos += Vector2(50.0f * cos(e.dataMove[0]) * deltaTime, worldSpeed * deltaTime)
    }),
    SINUSOID_WIDE({ e, worldSpeed, deltaTime ->
        e.dataMove[0] += deltaTime
        e.pos += Vector2(250.0f * cos(e.dataMove[0]) * deltaTime, worldSpeed * deltaTime)
    }),
    SINUSOID_FAST_NARROW({ e, worldSpeed, deltaTime ->
        e.dataMove[0] += deltaTime
        e.pos += Vector2(50.0f * cos(e.dataMove[0]) * deltaTime, 1.4 * worldSpeed * deltaTime)
    }),
    SINUSOID_FAST_WIDE({ e, worldSpeed, deltaTime ->
        e.dataMove[0] += deltaTime
        e.pos += Vector2(250.0f * cos(e.dataMove[0]) * deltaTime, 1.4 * worldSpeed * deltaTime)
    })
}