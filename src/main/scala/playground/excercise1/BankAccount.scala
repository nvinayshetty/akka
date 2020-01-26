package playground.excercise1

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import playground.excercise1.BankAccount.BankAccountActor.{Deposit, WithDraw}

object BankAccount extends App {
  val actorSystem = ActorSystem("BankAccount")

  object BankAccountActor{
    case class Deposit( amount:Int,ref:ActorRef)
    case class WithDraw(amount: Int,ref:ActorRef)
  }

  class BankAccountActor() extends Actor{

    private var fund=0
    override def receive: Receive = {
      case deposit:Deposit => {
        fund += deposit.amount
        deposit.ref forward deposit
      }
      case withDraw: WithDraw => {
        fund -= withDraw.amount
        withDraw.ref forward withDraw
      }
      case Statement => println(s"Current balance $fund")
    }
  }

  class LoggerActor extends Actor{
    override def receive: Receive = {
      case deposit:Deposit => println(s"Deposited:${deposit.amount}")
      case withDraw: WithDraw => println(s"withDrawn:${withDraw.amount}")
    }

  }


  case class  Statement()

  val account=actorSystem.actorOf(Props[BankAccountActor],"bankAccountActor")
  val logger=  actorSystem.actorOf(Props[LoggerActor],"logger")

  account ! Deposit(10,logger)
  account ! WithDraw(20,logger)

}
