package robot.fabric

import robot.heads.IHead


class IHeadFabric(val price: Int, val brandName: String) : Fabric<IHead> {
    override fun getObj(): IHead {
        return IHead(price, brandName)
    }
}