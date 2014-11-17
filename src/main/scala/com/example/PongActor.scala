package com.example

import akka.actor.{Actor, ActorLogging, Props}

class PongActor extends Actor with ActorLogging {
  import PongActor._

  private var pongCounter = 0

  def receive = {
  	case PingActor.PingMessage(text) => 
  	  log.info("In PongActor - received message: {}", text)
  	  pongCounter += 1
      sender() ! PongMessage(s"pong-$pongCounter")
  }	
}

object PongActor {
  val props = Props[PongActor]
  case class PongMessage(text: String)
}
