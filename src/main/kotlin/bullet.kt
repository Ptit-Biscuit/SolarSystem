import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.math.Vector2

class Bullet(var pos: Vector2, var vel: Vector2, var exists: Boolean = true) {
    fun update(drawer: Drawer, worldSpeed: Double, deltaTime: Double) {
        drawer.fill = ColorRGBa.WHITE
        drawer.circle(this.pos, 6.0)
        this.pos += (this.vel + Vector2.UNIT_Y * worldSpeed) * deltaTime
    }
}