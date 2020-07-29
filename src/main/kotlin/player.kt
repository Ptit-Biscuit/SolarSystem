import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.math.Vector2

class Player(var pos: Vector2, var health: Int = 3) {
    val scale = 20.0
    private var firingRate = .2
    private var lastFire = -1.0

    fun update(drawer: Drawer, position: Vector2) {
        if (position.x > this.scale * 1.5 || position.x < drawer.bounds.width - this.scale * 1.5
            || position.y > this.scale * 1.5 || position.y < drawer.bounds.height - this.scale * 1.5
        ) {
            this.pos = position
        }
    }

    fun draw(drawer: Drawer) {
        drawer.stroke = ColorRGBa.YELLOW
        drawer.fill = ColorRGBa.TRANSPARENT

        drawer.lineStrip(
            listOf(
                this.pos + Vector2(-1.0, 1.0) * this.scale,
                this.pos - Vector2.UNIT_Y * this.scale,
                this.pos + Vector2.ONE * this.scale,
                this.pos + Vector2(-1.0, 1.0) * this.scale
            )
        )

        drawer.stroke = ColorRGBa.WHITE
    }

    fun shoot(seconds: Double, bullets: MutableList<Bullet>) {
        if (seconds - this.lastFire > this.firingRate || lastFire == -1.0) {
            bullets.add(Bullet(true, this.pos - Vector2.UNIT_Y * this.scale))
            this.lastFire = seconds
        }
    }
}