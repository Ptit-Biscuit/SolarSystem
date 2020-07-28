import org.openrndr.draw.Drawer
import org.openrndr.math.Vector2

abstract class Ship(var pos: Vector2, var health: Int) {
    protected open val scale = 1.0
    protected open val bullets = mutableListOf<Bullet>()
    protected open var bulletSpeed = 1.0
    protected open var firingRate = 1.0
    protected open var lastFire = -1.0

    abstract fun draw(drawer: Drawer)
    abstract fun shoot(seconds: Double)
    abstract fun hit(bullet: Bullet)
    abstract fun die()
}