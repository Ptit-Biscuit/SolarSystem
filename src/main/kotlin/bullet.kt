import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.math.Vector2

class Bullet(val fromPlayer: Boolean, var pos: Vector2) {
    fun update(drawer: Drawer, speed: Double) {
        drawer.fill = ColorRGBa.WHITE
        drawer.circle(this.pos, 6.0)
        this.pos -= Vector2.UNIT_Y * speed
    }
}