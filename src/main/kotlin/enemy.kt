import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.math.Vector2

data class EnemyDefinition(val movement: Movement, val fire: Fire)

class Enemy(pos: Vector2, val def: EnemyDefinition) : Ship(pos, 1) {
    override var bulletSpeed = -3.0
    override var firingRate = .8

    fun update(drawer: Drawer, seconds: Double) {
        this.pos += this.def.movement.pattern(seconds)
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

        drawer.stroke = ColorRGBa.WHITE
    }

    override fun shoot(seconds: Double) {
        if (seconds - this.lastFire > this.firingRate || lastFire == -1.0) {
            this.bullets.addAll(this.def.fire.pattern(this, seconds))
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