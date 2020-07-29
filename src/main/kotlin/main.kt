import org.openrndr.KEY_SPACEBAR
import org.openrndr.MouseButton
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noise.random
import org.openrndr.math.Vector2

fun main() = application {
    configure {
        width = 900
        height = 600
    }

    program {
        val stars = MutableList(1000) { Vector2(random(.0, width.toDouble()), random(.0, height.toDouble())) }
        val player = Player(Vector2(width / 2.0, height - 50.0))

        val enemyDefinition = EnemyDefinition(Movement.SIMPLE, Fire.SIN)
        val enemyDefinitionBis = EnemyDefinition(Movement.SIN, Fire.SIMPLE)
        val enemy = Enemy(Vector2(width * .25, -50.0), enemyDefinition)
        val enemyBis = Enemy(Vector2(width * .75, -50.0), enemyDefinitionBis)

        val enemies = mutableListOf(enemy, enemyBis)

        mouse.buttonDown.listen {
            if (it.button == MouseButton.LEFT) {
                player.shoot(seconds)
            }
        }

        keyboard.keyRepeat.listen {
            if (it.key == KEY_SPACEBAR) {
                player.shoot(seconds)
            }
        }

        extend {
            stars.forEachIndexed { i, it ->
                drawer.fill = if (i < 300) ColorRGBa.GRAY else ColorRGBa.WHITE
                drawer.circle(it, 2.3)

                stars[i] = it + Vector2(.0, 20.0 * deltaTime * (if (i < 300) .8 else 1.0))

                if (it.y > height) {
                    stars[i] = Vector2(random(.0, width.toDouble()), .0)
                }
            }

            player.draw(drawer)
            player.update(drawer, mouse.position)

            enemies.forEach {
                it.draw(drawer)
                it.update(drawer, seconds)
            }
        }
    }
}
