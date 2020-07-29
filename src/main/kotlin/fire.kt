import org.openrndr.math.Vector2
import kotlin.math.sin

enum class Fire(val pattern: (Enemy, Double) -> List<Bullet>) {
    SIMPLE({ e, _ -> listOf(Bullet(false, e.pos + Vector2.UNIT_Y * 20.0)) }),
    SIN({ e, s -> listOf(Bullet(false, e.pos + Vector2(20.0 * sin(s), 20.0))) })
}