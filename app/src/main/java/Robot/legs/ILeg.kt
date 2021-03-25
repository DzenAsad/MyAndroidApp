package kotlinRobot.legs

import Price
import kotlinRobot.Brand

class ILeg(override val price: Int, override val brandName: String) : Price, Brand {
    fun step() {
        println("Идёт нога $brandName")
    }
}