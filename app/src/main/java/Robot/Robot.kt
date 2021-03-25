import kotlinRobot.hands.IHand
import kotlinRobot.heads.IHead
import kotlinRobot.legs.ILeg

class Robot (val head: IHead, val hand: IHand, val leg: ILeg): IRobot{
    override val price: Int = head.price + hand.price + leg.price
}