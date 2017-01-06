package com.example

import akka.actor.ActorSystem

object ApplicationMain extends App {
  // An actor system is typically started by creating actors beneath the guardian
  // actor. By using the ActorSystem.actorOf method and then using
  // ActorContext.actorOf from within the created actors to spawn the actor tree.
  val system = ActorSystem("MyActorSystem")
  
  // All actors are created with "context.actorOf(...)"
  // Using the Context we created above, "system", we can create a
  // a top-level actor
  // It is supervised by the "Guardian" Actor.

  // Create the top-level PING Actor
  // Props is a configuration object using in creating an actor
  // it is immutable, so it is thread-safe and fully shareable.
  val pingActor = system.actorOf(PingActor.props, "pingActor")
  
  // The "!" symbol is actually a scala method and is a shortcut for the tell() method.
  // ! means “fire-and-forget”, e.g. send a message asynchronously and
  // return immediately.
  // This is the preferred way of sending messages.
  // No blocking waiting for a message.
  // This gives the best concurrency and scalability characteristics.
  
  // This example app will ping-pong 3 times and
  // You can see counter logic in PingActor
  pingActor ! PingActor.Initialize
 
  // Trminate the Actor System 
  system.awaitTermination()
}
