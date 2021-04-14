package robot.heads

import robot.Price
import robot.Brand

class IHead(override val price: Int, override val brandName: String) : Price, Brand {
    fun speak() {
        println("Говорит голова $brandName")
    }
}