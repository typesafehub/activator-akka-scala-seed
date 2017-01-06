package com.example

import akka.actor.{Actor, ActorLogging, Props}

// Define the class and Mix In the LOGGING trait,
// so we can crteate log entries of what is happening
class PongActor extends Actor with ActorLogging {
  import PongActor._

  // Define the receive method and pattern-match for the messages
  // we can successfully handle
  def receive = {
  	case PingActor.PingMessage(text) => 
  	  // Create a log message
          log.info("In PongActor - received message: {}", text)
  	  
          // Send the "pong" message back, to the sender.
          // sender() is a special case, it is a reference to the sender of the message received.
          sender() ! PongMessage("pong")
  }	
}

object PongActor {
  // Besides keeping the Props creation closer to the actor's class,
  // it also avoids accidentally closing over an actor's this reference,
  // as would happen if you used Props(new MyActor("foo")) inside another actor.

  // This also avoids the pitfalls associated with using the Props.apply(...) method
  // which takes a by-name argument, since within a companion object the given
  // code block will not retain a reference to its enclosing scope.
  val props = Props[PongActor]
 
  case class PongMessage(text: String)
}
