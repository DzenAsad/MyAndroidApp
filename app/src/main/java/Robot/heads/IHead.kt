package kotlinRobot.heads

import Price
import kotlinRobot.Brand

class IHead(override val price: Int, override val brandName: String) : Price, Brand {
    fun speak() {
        println("Говорит голова $brandName")
    }
}