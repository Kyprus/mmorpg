package mmorpg.world

import akka.actor.ActorRef

object WorldMessage {

  sealed trait WorldMessage

  case class ClientConnected(worker: ActorRef) extends WorldMessage
  case class ClientDisconnected(worker: ActorRef) extends WorldMessage

  case class MovePlayer(worker: ActorRef, direction: Int)
}