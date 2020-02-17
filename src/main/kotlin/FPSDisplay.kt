import org.openrndr.Extension
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.draw.isolated
import org.openrndr.math.Matrix44

class FPSDisplay : Extension {
    override var enabled: Boolean = true

    private var frames = 0
    var startTime: Double = 0.0

    override fun setup(program: Program) {
        startTime = program.seconds
    }

    override fun afterDraw(drawer: Drawer, program: Program) {
        frames++

        drawer.isolated {
            // -- set view and projections
            view = Matrix44.IDENTITY
            ortho()
            fill = ColorRGBa.WHITE

            text("fps: ${frames / (program.seconds - startTime)}")
        }
    }
}