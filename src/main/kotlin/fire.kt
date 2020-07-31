import org.openrndr.math.Vector2
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

enum class Fire(val pattern: (Enemy, Double, Double, MutableList<Bullet>) -> Unit) {
    NONE({ _, _, _, _ -> }),
    SIMPLE({ e, _, deltaTime, bullets ->
        val delay = .5
        e.dataFire[0] += deltaTime

        if (e.dataFire[0] >= delay) {
            e.dataFire[0] -= delay

            bullets.add(Bullet(e.pos + Vector2.UNIT_Y * e.scale, Vector2.UNIT_Y * 100.0))
        }
    }),
    CIRCLE_PULSE({ e, _, deltaTime, bullets ->
        val delay = 1.0
        val nBullets = 10
        val theta = 2.0 * PI / nBullets

        e.dataFire[0] += deltaTime

        if (e.dataFire[0] >= delay) {
            e.dataFire[0] -= delay

            (0 until nBullets).forEach {
                bullets.add(
                    Bullet(
                        e.pos + Vector2.UNIT_Y * e.scale,
                        Vector2(180.0 * cos(theta * it), 180.0 * sin(theta * it))
                    )
                )
            }
        }
    }),
    DEATH_SPIRAL_CIRCLE({ e, _, deltaTime, bullets ->
        val delay = .2
        val nBullets = 100
        val theta = 2.0 * 3.14159 / nBullets
        e.dataFire[0] += deltaTime
        if (e.dataFire[0] >= delay) {
            e.dataFire[0] -= delay
            e.dataFire[1] += .1

            (0 until nBullets).forEach {
                bullets.add(
                    Bullet(
                        e.pos + Vector2.UNIT_Y * e.scale,
                        Vector2(180.0f * cos(theta * it + e.dataFire[1]), 180.0f * sin(theta * it + e.dataFire[1]))
                    )
                )
            }
        }
    })
}