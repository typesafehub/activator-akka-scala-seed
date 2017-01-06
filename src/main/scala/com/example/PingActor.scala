package com.example

import akka.actor.{Actor, ActorLogging, Props}

// Define the class and Mix In the LOGGING trait,
// so we can crteate log entries of what is happening

class PingActor extends Actor with ActorLogging {
  import PingActor._
  
  // Initialise a counter so we know when we have done this 3 times.
  var counter = 0

  // Create a PongActor as a child of the PingActor
  val pongActor = context.actorOf(PongActor.props, "pongActor")

  // Define the receive method : which uses pattern matching to define what to do
  // for all the expected messages
  def receive = {
  	// What do do when we receive a "Initialize" message
        case Initialize => 
	  // Create a log message  
          log.info("In PingActor - starting ping-pong")

          // To the "pongActor" send a PingMessage
  	  pongActor ! PingMessage("ping")	

        // Define what to do when the PongActor sends us a PongMessage
  	case PongActor.PongMessage(text) =>
  	  // Create a log message
          log.info("In PingActor - received message: {}", text)
          
          // Increment the counter
          counter += 1
  	  
          // If we have reached 3 : shutdown the system.
          if (counter == 3) context.system.shutdown()
          
          // If we haven't reached 3 yet, send a PING, back to the "sender", actor.
  	  else sender() ! PingMessage("ping")
  }	
}

// Define our companion object
object PingActor {
  // Besides keeping the Props creation closer to the actor's class, 
  // it also avoids accidentally closing over an actor's this reference, 
  // as would happen if you used Props(new MyActor("foo")) inside another actor.

  // This also avoids the pitfalls associated with using the Props.apply(...) method 
  // which takes a by-name argument, since within a companion object the given 
  // code block will not retain a reference to its enclosing scope.
  val props = Props[PingActor]

  // A case object and case class are "pretty much identical". 
  // The difference being that if you're creating a case class without any arguments, 
  // then you should create a case object, instead.
  // Since they are immutable (no arguments to change any state).... why would you ever need multiple instances? 
  case object Initialize

  // 
  case class PingMessage(text: String)
}
