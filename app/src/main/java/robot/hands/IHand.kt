package robot.hands

import robot.Price
import robot.Brand

class IHand(override val price: Int, override val brandName: String) : Price, Brand {
    fun upHand() {
        println("Машет рука $brandName" )
    }
}