import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.math.Vector2

class Player(pos: Vector2) : Ship(pos, 3) {
    override var bulletSpeed = 4.0
    override var firingRate = .2

    fun update(drawer: Drawer, position: Vector2) {
        if (position.x > this.scale || position.x < drawer.bounds.width - this.scale
            || position.y > this.scale || position.y < drawer.bounds.height - this.scale
        ) {
            this.pos = position
        }

        this.bullets.forEach { it.update(drawer, this.bulletSpeed) }
    }

    override fun draw(drawer: Drawer) {
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

    override fun shoot(seconds: Double) {
        if (seconds - this.lastFire > this.firingRate || lastFire == -1.0) {
            this.bullets.add(Bullet(true, this.pos - Vector2.UNIT_Y * this.scale))
            this.lastFire = seconds
        }
    }

    override fun hit(bullet: Bullet) {
        if (!bullet.fromPlayer) {
            this.health--
        }

        if (this.health <= 0) {
            this.die()
        }
    }

    override fun die() {
        println("GAME OVER")
    }
}