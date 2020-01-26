package playground.excercise1

import akka.actor.{Actor, ActorSystem, Props}

object Counter  extends App{
  val actorSystem = ActorSystem("HelloAkka")

  class CounterActor extends Actor{
    private var count=0
    override def receive: Receive =  {
      case increment: Increment => count= count + increment.count
      case decrement: Decrement => count = count - decrement.count
      case Print => println(s"Current value is $count")

    }
  }

  case class Increment(count:Int)
  case class Decrement(count:Int)
  case class Print()

  val counterActor = actorSystem.actorOf(Props[CounterActor],"counter")
  counterActor ! Increment(5)
  counterActor ! Print
  counterActor ! Decrement(5)
  counterActor ! Decrement(5)
  counterActor ! Decrement(5)
  counterActor ! Print


}
