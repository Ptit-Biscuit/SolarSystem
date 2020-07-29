import org.openrndr.math.Vector2
import kotlin.math.sin

enum class Movement(val pattern: (Enemy, Double, Double) -> Unit) {
    NONE({ e, worldSpeed, seconds -> e.pos += Vector2.UNIT_Y * worldSpeed * seconds }),
    STRAIGHT_FAST({ e, worldSpeed, seconds -> e.pos += Vector2(.0, 3.0) * worldSpeed * seconds }),
    STRAIGHT_SLOW({ e, worldSpeed, seconds -> e.pos += Vector2(.0, .5) * worldSpeed * seconds }),
    SIN({ e, worldSpeed, seconds -> e.pos += Vector2(sin(seconds), worldSpeed * seconds) })
}