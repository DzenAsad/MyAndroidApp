package robot.legs

import robot.Price
import robot.Brand

class ILeg(override val price: Int, override val brandName: String) : Price, Brand {
    fun step() {
        println("Идёт нога $brandName")
    }
}