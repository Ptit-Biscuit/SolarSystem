import org.openrndr.math.Vector2
import kotlin.math.sin

enum class Movement(val pattern: (Double) -> Vector2) {
    SIMPLE({ Vector2.UNIT_Y }),
    SIN({ Vector2(sin(it), .5) })
}