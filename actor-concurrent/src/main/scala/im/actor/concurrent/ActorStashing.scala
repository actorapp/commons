package im.actor.concurrent

import akka.actor.{ Stash, ActorRef, Actor }

trait ActorStashing extends Actor with Stash {
  protected def becomeStashing(f: ActorRef ⇒ Receive, discardOld: Boolean = false): Unit =
    context.become(receiveStashing(f), discardOld = discardOld)

  protected def receiveStashing(f: ActorRef ⇒ Receive): Receive =
    f(sender()) orElse stashing

  private def stashing: Receive = {
    case msg ⇒ stash()
  }
}
