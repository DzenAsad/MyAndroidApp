package kotlinRobot.fabric

import kotlinRobot.hands.IHand

class IHandFabric(val price: Int, val brandName: String) : Fabric<IHand> {
    override fun getObj(): IHand {
        return IHand(price, brandName)
    }
}