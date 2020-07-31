import org.openrndr.KEY_SPACEBAR
import org.openrndr.MouseButton
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.loadFont
import org.openrndr.extra.noise.random
import org.openrndr.math.Vector2

fun mag2(v: Vector2) = v.x * v.x + v.y * v.y

const val showStars = false

fun main() = application {
    configure {
        width = 900
        height = 600
    }

    program {
        val font = loadFont("src/main/resources/VCR_OSD_MONO_1.001.ttf", 48.0)

        var worldPos = .0
        val worldSpeed = 100.0

        val stars = MutableList(1000) { Vector2(random(.0, width.toDouble()), random(.0, height.toDouble())) }
        val player = Player(Vector2(width / 2.0, height - 50.0))

        val enemySpawner = mutableListOf<EnemyDefinition>()
        val enemiesBullets = mutableListOf<Bullet>()

        (0..50 step 50).forEach {
            enemySpawner.add(
                EnemyDefinition(
                    it.toDouble(),
                    1,
                    random(.2, .98),
                    Movement.values().random(),
                    Fire.values().random()
                )
            )
        }

        val enemies = mutableListOf<Enemy>()

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
            drawer.fontMap = font
            drawer.stroke = ColorRGBa.WHITE

            if (player.health <= 0) {
                drawer.text("GAME OVER", width / 2.0 - 200.0, height / 2.0)

                enemySpawner.clear()
                enemies.clear()
            }

            worldPos += worldSpeed * deltaTime

            if (showStars) {
                stars.forEachIndexed { i, it ->
                    drawer.fill = if (i < 300) ColorRGBa.GRAY else ColorRGBa.WHITE
                    drawer.circle(it, 2.0)

                    stars[i] = it + Vector2(.0, worldSpeed * deltaTime * (if (i < 300) .6 else .8))

                    if (it.y > height) {
                        stars[i] = Vector2(random(.0, width.toDouble()), .0)
                    }
                }
            }

            enemySpawner.filter {
                worldPos >= it.triggerPos
            }.forEach {
                enemies.add(Enemy(Vector2(it.xOffset * width, .0), it))
                enemySpawner.remove(it)
            }

            enemies.forEach {
                it.draw(drawer)
                it.update(player, enemiesBullets, worldSpeed, deltaTime)
            }

            player.draw(drawer)
            player.update(drawer, mouse.position, enemies, worldSpeed, deltaTime)

            enemiesBullets.forEach {
                it.update(drawer, worldSpeed, deltaTime)

                if (mag2(it.pos - player.pos) < player.scale * player.scale) {
                    it.exists = false
                    player.health--
                }
            }

            enemies.removeIf { it.pos.y >= height || it.def.health <= 0 }
            enemiesBullets.removeIf { !(drawer.bounds.contains(it.pos) && it.exists) }
        }
    }
}
