package org.example.application
package actors

import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import GreeterMain.SayHello

/*
  Fundamentally, an actor is an object that receives messages and takes action on those
  messages, one at a time and without preemption. The order in which messages arrive
  is unimportant in some actor systems, but not all. An actor might process a message
  internally, or it might forward the message or send a new message to another actor.

  An actor might create new actors as part of handling a message. An actor might change
  how it handles messages, in effect implementing a state transition in a state machine.

  Actors are created to do the work, supervisors watch the lifecycle of one or more actors.
  Should an actor fail, perhaps because an exception is thrown, the supervisor follows a
  strategy for recovery that can include restarting, shutting down, ignoring the error, or
  delegating to its superior for handling.

  ----

  An actor encapsulates state and behaviour.
  Other parts of a system can only interact with an actor by putting messages into a queue, the actor mailbox.
  The actor system then makes sure that actors only process a single message at a time
  An actor can:
    Modify its state
    Change how it behaves for the next message
    Send messages to other actors

  We define:
    A class which is our actor, this should implement abstract behaviour
    An interface which defines the type of object the actor acts on. This provides some extra flexibility in case
    we want to add new classes
    Generally these are treated as singletons and require an actor context to operate against.
    We then define a number of behaviours we want to execute when we receive a command

  Actors always run inside of an actor system. We create a system and then tell it commands
 */


object Greeter {
  /*
    The greet class is used to define the message we expect to receive. In the apply method we define the behaviour
    we expect when we get that message. We also receive an actor which we tell that we have got their message
   */
  final case class Greet(whom: String, replyTo: ActorRef[Greeted])
  final case class Greeted(whom: String, from: ActorRef[Greet])

  // Note the use of Behaviours receive. This is how we define what we want the actor to do when it receives a message.
  // In Java we need the class to extend an abstract behaviour, but in Scala it looks like we only need an object with
  // an apply method
  def apply(): Behavior[Greet] = Behaviors.receive { (context, message) =>
    println(s"Hello ${message.whom}!")

    // Note, the exclamation mark is shorthand for 'tell', which sends a message to an actor asynchronously and
    // doesn't wait for a response. A question mark returns a future we can use to check when the message is ingested
    message.replyTo ! Greeted(message.whom, context.self)

    // Here we return the same behaviour. As described in the intro, we could potentially change the behaviour of
    // the class after receiving a message, however in this case we do not
    Behaviors.same
  }
}

object GreeterBot {

  // This is also an actor, but instead of working on a Greet object works on a Greeted object. So the idea is that we
  // send an initial person (person A), who greets another person (person B), who greets the person back (person A),
  // who then re-greets the person up until a maximum number of times has been reached
  def apply(max: Int): Behavior[Greeter.Greeted] = {
    bot(0, max)
  }

  // This is a wrapper round the counter class
  private def bot(greetingCounter: Int, max: Int): Behavior[Greeter.Greeted] =
    Behaviors.receive { (context, message) =>
      // Each time we receive a message we increase the counter by one. At the end of this function we will send
      // a message to the greeter
      val n = greetingCounter + 1
      println(s"Greeting ${n} for ${message.whom}")

      // Here we see a change of behaviour for the actor when we reach a certain limit. Instead of recursively calling
      // this function and sending a
      if (n == max) {
        Behaviors.stopped
      } else {
        message.from ! Greeter.Greet(message.whom, context.self)
        bot(n, max)
      }
    }
}

object GreeterMain {

  // Internal definition of the class we will be basing our behaviours round
  final case class SayHello(name: String)

  // This defines the behaviour of the actor. Note how it uses 'setup' rather than 'receive'. This is so it executes
  // immediately and doesn't need to wait to receive a message on the queue.
  def apply(): Behavior[SayHello] =
    Behaviors.setup { context =>

      // This is used to spawn a reference to a Greeter actor (hence ActorRef). This is powerful as it doesn't require
      // the actor to be running on the machine, it could be anywhere!
      val greeter = context.spawn(Greeter(), "greeter")

      // Now we return a behaviour for receiving messages. Notice I don't think we do anything with this behaviour, we
      // just need to return a behaviour from the method
      Behaviors.receiveMessage { message =>

        // Here we also spawn a greeter bot which takes in the same name
        val replyTo = context.spawn(GreeterBot(max = 3), message.name)

        // Finally we pass the greeter object to the greeter actor, along with the greeter bot, which provides somewhere
        // to reply to
        greeter ! Greeter.Greet(message.name, replyTo)
        Behaviors.same
      }
    }
}

object AkkaQuickstart extends App {

  // This is the actor system we need to create in order to send orders to. Notice how it instantiates itself with
  // the first actor. This is so we can send the first 'SayHello' message to it.
  val greeterMain: ActorSystem[GreeterMain.SayHello] = ActorSystem(GreeterMain(), "AkkaQuickStart")

  // Here we tell the GreeterMain actor to 'say hello'. There must be some imports of implicits somewhere in the chain in
  // order to tell the compiler they are actors
  greeterMain ! SayHello("Charles")

}

