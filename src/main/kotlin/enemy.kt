import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.math.Vector2

class Enemy(pos: Vector2) : Ship(pos, 1) {
    override val scale = 30.0
    override val bullets = mutableListOf<Bullet>()
    override var bulletSpeed = -3.0
    override var firingRate = .5
    override var lastFire = -1.0

    fun update(drawer: Drawer, seconds: Double) {
        this.draw(drawer)

        this.shoot(seconds)
        this.bullets.forEach { it.update(drawer, this.bulletSpeed) }
    }

    override fun draw(drawer: Drawer) {
        drawer.stroke = ColorRGBa.RED
        drawer.fill = ColorRGBa.TRANSPARENT

        drawer.lineStrip(
            listOf(
                this.pos - Vector2(-1.0, 1.0) * this.scale,
                this.pos + Vector2.UNIT_Y * this.scale,
                this.pos - Vector2.ONE * this.scale,
                this.pos - Vector2(-1.0, 1.0) * this.scale
            )
        )

        this.pos += Vector2.UNIT_Y

        drawer.stroke = ColorRGBa.WHITE
    }

    override fun shoot(seconds: Double) {
        if (seconds - this.lastFire > this.firingRate || lastFire == -1.0) {
            this.bullets.add(Bullet(false, this.pos + Vector2.UNIT_Y * this.scale))
            this.lastFire = seconds
        }
    }

    override fun hit(bullet: Bullet) {
        if (bullet.fromPlayer) {
            this.health--
        }

        if (this.health <= 0) {
            this.die()
        }
    }

    override fun die() {
        println("enemy died")
    }
}