import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.math.Vector2

class Player(var pos: Vector2, var health: Int = 3) {
    val scale = 20.0
    private val bullets = mutableListOf<Bullet>()
    private var firingRate = .2
    private var lastFire = -1.0

    fun update(drawer: Drawer, position: Vector2, enemies: List<Enemy>, worldSpeed: Double, deltaTime: Double) {
        if (position.x > this.scale * this.scale || position.x < drawer.bounds.width - this.scale * this.scale
            || position.y > this.scale * this.scale || position.y < drawer.bounds.height - this.scale * this.scale
        ) {
            this.pos = position
        }

        this.bullets.forEach { b ->
            b.update(drawer, worldSpeed, deltaTime)

            enemies.forEach {
                if (mag2(b.pos - it.pos) < it.scale * it.scale) {
                    b.exists = false
                    it.def.health--
                }
            }
        }

        this.bullets.removeIf { !(drawer.bounds.contains(it.pos) && it.exists) }
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

    fun shoot(seconds: Double) {
        if (seconds - this.lastFire > this.firingRate || lastFire == -1.0) {
            this.bullets.add(Bullet(this.pos - Vector2.UNIT_Y * this.scale, -Vector2.UNIT_Y * 200.0))
            this.lastFire = seconds
        }
    }
}