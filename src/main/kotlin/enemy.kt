import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.math.Vector2

data class EnemyDefinition(
    var triggerPos: Double,
    var health: Int,
    val xOffset: Double,
    val move: Movement,
    val fire: Fire
)

class Enemy(var pos: Vector2, val def: EnemyDefinition) {
    val scale = 20.0
    val dataMove = MutableList(4) { .0 }
    val dataFire = MutableList(4) { .0 }

    fun update(worldSpeed: Double, deltaTime: Double, bullets: MutableList<Bullet>) {
        this.def.move.pattern(this, worldSpeed, deltaTime)
        this.def.fire.pattern(this, worldSpeed, deltaTime, bullets)
    }

    fun draw(drawer: Drawer) {
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
}