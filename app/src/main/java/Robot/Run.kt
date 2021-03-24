import kotlinRobot.fabric.RobotFabric
import kotlin.random.Random

fun main() {
    val listBrands = listOf("Samsung", "Toshiba", "Lenovo")
    val r: () -> Int = { Random.nextInt(100) }


    listBrands.forEach {
        val robotFabric = RobotFabric(it, r())
        val robot = robotFabric.getObj()
        robot.head.speak()
        robot.hand.upHand()
        robot.leg.step()
        println("Цена: ${robot.price}")
    }
}


