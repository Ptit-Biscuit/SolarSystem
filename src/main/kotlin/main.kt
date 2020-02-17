import org.openrndr.*
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.isolatedWithTarget
import org.openrndr.draw.renderTarget
import org.openrndr.math.Vector2
import org.openrndr.text.Writer
import kotlin.random.Random

data class SolarSystemBounds(val minCoords: Vector2, val maxCoords: Vector2)

class Planet(val coords: Vector2) {
    val size = 50.0
}

fun main() = application {
    configure {
        width = 900
        height = 900
    }

    program {
        val rt = renderTarget(800, 800) {
            colorBuffer()
        }

        val solarSystemBounds = SolarSystemBounds(Vector2(0.0, 0.0), Vector2(400.0, 400.0))
        var planets = mutableListOf<Planet>()
        var toto = Vector2(width / 2.0, height / 2.0)

        keyboard.keyUp.listen {
            if (it.key == KEY_SPACEBAR) {
                planets = generateSolarSystem(0.0, solarSystemBounds)
                planets.forEach { planet -> println("Planet: ${planet.coords}") }
            }
        }

        keyboard.keyRepeat.listen {
            toto += Vector2(
                if (it.key == KEY_ARROW_RIGHT) 10.0 else if (it.key == KEY_ARROW_LEFT) -10.0 else 0.0,
                if (it.key == KEY_ARROW_UP) 10.0 else if (it.key == KEY_ARROW_DOWN) -10.0 else 0.0
            )
        }

        extend {
            drawer.fill = ColorRGBa.WHITE

            writer {
                box = Rectangle(40.0, 40.0, 300.0, 300.0)
                newLine()
                text("Here is a line of text..")
                newLine()
                text("Here is another line of text..")
            }

            drawer.translate(toto)

            drawer.isolatedWithTarget(rt) {
                background(ColorRGBa.BLACK)

                planets.forEach {
                    circle(it.coords, it.size)
                }
            }

            drawer.image(rt.colorBuffer(0))
        }
    }
}

fun generateSolarSystem(seed: Double, solarSystemBounds: SolarSystemBounds): MutableList<Planet> {
    val random = Random(seed.toLong())
    return mutableListOf(
        Planet(
            Vector2(
                random.nextDouble(solarSystemBounds.minCoords.x, solarSystemBounds.maxCoords.x),
                random.nextDouble(solarSystemBounds.minCoords.y, solarSystemBounds.maxCoords.y)
            )
        )
    )
}
