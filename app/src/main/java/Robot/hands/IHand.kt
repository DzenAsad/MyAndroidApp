package kotlinRobot.hands

import Price
import kotlinRobot.Brand

class IHand(override val price: Int, override val brandName: String) : Price, Brand {
    fun upHand() {
        println("Машет рука $brandName" )
    }
}