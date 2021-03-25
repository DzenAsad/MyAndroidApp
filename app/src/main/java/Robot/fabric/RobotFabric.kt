package kotlinRobot.fabric


import Robot


class RobotFabric(brandName: String, price: Int): Fabric<Robot> {
    val iHeadFabric: IHeadFabric = IHeadFabric(price, brandName)
    val iHandFabric: IHandFabric = IHandFabric(price, brandName)
    val iLegFabric: ILegFabric = ILegFabric(price, brandName)

    override fun getObj(): Robot {
        return Robot(iHeadFabric.getObj(), iHandFabric.getObj(), iLegFabric.getObj())
    }
}