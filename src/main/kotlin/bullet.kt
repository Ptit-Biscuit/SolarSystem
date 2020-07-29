import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.math.Vector2

class Bullet(var exists: Boolean, var pos: Vector2) {
    fun update(drawer: Drawer, worldSpeed: Double, deltaTime: Double) {
        drawer.fill = ColorRGBa.WHITE
        drawer.circle(this.pos, 6.0)
        this.pos -= Vector2.UNIT_Y * worldSpeed * deltaTime
    }
}