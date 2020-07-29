import org.openrndr.math.Vector2
import kotlin.math.sin

enum class Fire(val pattern: (Enemy, Double, Double, MutableList<Bullet>) -> Unit) {
    NONE({ _, _, _, _ -> }),
    SIMPLE({ e, _, deltaTime, bullets -> bullets.add(Bullet(true, e.pos + Vector2.UNIT_Y * 20.0 * deltaTime)) }),
    SIN({ e, _, deltaTime, bullets -> bullets.add(Bullet(true, e.pos + Vector2(20.0 * sin(deltaTime), 20.0))) })
}