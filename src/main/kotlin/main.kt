import org.openrndr.KEY_SPACEBAR
import org.openrndr.MouseButton
import org.openrndr.application
import org.openrndr.math.Vector2

fun main() = application {
    configure {
        width = 900
        height = 600
    }

    program {
        val player = Player(Vector2(width / 2.0, height - 50.0))
        val enemy = Enemy(Vector2(width / 2.0, -50.0))

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
            player.draw(drawer)
            player.update(drawer, mouse.position)
            enemy.update(drawer, seconds)
        }
    }
}
