package robot

import robot.hands.IHand
import robot.heads.IHead
import robot.legs.ILeg

class Robot (val head: IHead, val hand: IHand, val leg: ILeg): IRobot {
    override val price: Int = head.price + hand.price + leg.price
}