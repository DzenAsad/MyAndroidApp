package kotlinRobot.fabric

import kotlinRobot.legs.ILeg

class ILegFabric(val price: Int, val brandName: String) : Fabric<ILeg> {
    override fun getObj(): ILeg {
        return ILeg(price, brandName)
    }
}