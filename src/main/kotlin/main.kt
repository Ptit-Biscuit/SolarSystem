import org.openrndr.KEY_SPACEBAR
import org.openrndr.MouseButton
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noise.random
import org.openrndr.math.Vector2
import kotlin.math.sqrt

fun main() = application {
    configure {
        width = 900
        height = 600
    }

    program {
        var worldPos = .0
        val worldSpeed = 100.0

        val stars = MutableList(1000) { Vector2(random(.0, width.toDouble()), random(.0, height.toDouble())) }
        val player = Player(Vector2(width / 2.0, height - 50.0))
        val playerBullets = mutableListOf<Bullet>()

        val enemySpawner = mutableListOf(
            EnemyDefinition(10.0, 1, .25, Movement.NONE, Fire.SIN),
            EnemyDefinition(10.0, 1, .5, Movement.NONE, Fire.SIMPLE),
            EnemyDefinition(10.0, 1, .75, Movement.NONE, Fire.SIN),
            EnemyDefinition(25.0, 1, .33, Movement.SIN, Fire.NONE),
            EnemyDefinition(25.0, 1, .66, Movement.SIN, Fire.NONE),
            EnemyDefinition(100.0, 1, .33, Movement.STRAIGHT_SLOW, Fire.SIMPLE),
            EnemyDefinition(100.0, 1, .66, Movement.STRAIGHT_SLOW, Fire.SIMPLE),
            EnemyDefinition(200.0, 1, .25, Movement.STRAIGHT_FAST, Fire.SIN),
            EnemyDefinition(200.0, 1, .75, Movement.STRAIGHT_FAST, Fire.SIN)
        )

        val enemies = mutableListOf<Enemy>()
        val enemiesBullets = mutableListOf<Bullet>()

        mouse.buttonDown.listen {
            if (it.button == MouseButton.LEFT) {
                player.shoot(seconds, playerBullets)
            }
        }

        keyboard.keyRepeat.listen {
            if (it.key == KEY_SPACEBAR) {
                player.shoot(seconds, playerBullets)
            }
        }

        extend {
            worldPos += worldSpeed * deltaTime

            /*stars.forEachIndexed { i, it ->
                drawer.fill = if (i < 300) ColorRGBa.GRAY else ColorRGBa.WHITE
                drawer.circle(it, 2.3)

                stars[i] = it + Vector2(.0, worldSpeed * deltaTime * (if (i < 300) .8 else 1.0))

                if (it.y > height) {
                    stars[i] = Vector2(random(.0, width.toDouble()), .0)
                }
            }*/

            playerBullets.forEach { b ->
                b.update(drawer, worldSpeed * 2, deltaTime)

                enemies.forEach {
                    drawer.stroke = ColorRGBa.YELLOW
                    drawer.rectangle(it.pos, it.scale, it.scale)
                    drawer.stroke = ColorRGBa.WHITE

                    if (mag(b.pos - (it.pos + Vector2(it.scale, it.scale))) < player.scale) {
                        b.exists = false
                        it.def.health--
                    }
                }
            }

            enemySpawner
                .filter { worldPos >= it.triggerPos }
                .forEach {
                    enemies.add(Enemy(Vector2(it.xOffset * width, .0), it))
                    enemySpawner.remove(it)
                }

            enemies.forEach {
                it.draw(drawer)
                it.update(worldSpeed, deltaTime, enemiesBullets)
            }

            player.draw(drawer)
            player.update(drawer, mouse.position)

            enemies.removeIf { it.pos.y >= height || it.def.health <= 0 }
            enemiesBullets.removeIf { !(drawer.bounds.contains(it.pos) && it.exists) }
            playerBullets.removeIf { !(drawer.bounds.contains(it.pos) && it.exists) }
        }
    }
}

fun mag(v: Vector2) = sqrt(v.x * v.x + v.y * v.y)
