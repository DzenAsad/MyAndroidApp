package robot.fabric

import robot.legs.ILeg

class ILegFabric(val price: Int, val brandName: String) : Fabric<ILeg> {
    override fun getObj(): ILeg {
        return ILeg(price, brandName)
    }
}