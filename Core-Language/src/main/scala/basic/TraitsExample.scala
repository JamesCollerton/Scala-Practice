package org.example.application
package basic

object TraitsExample {

  /*
  This is a pure logging trait, that works exactly like an interface
 */
  trait Logging {
    def info(message: String): Unit
  }

  /*
    This is a trait with a message body, extending the previous class
   */
  trait StdOutLogging extends Logging {
    def info(message: String): Unit = println(message)
  }

  abstract class Service {
    def doWork(): Unit
  }

  class LoggedService extends Service with StdOutLogging {
    override def doWork(): Unit = info("Doing logged service work!")
  }

  def main(args: Array[String]): Unit = {

    /*
      Here we show how we can do the mix in using an anonymous class
     */
    val mixedInService = new Service with StdOutLogging {
      override def doWork(): Unit = info("Doing anonymous service work!")
    }
    mixedInService.doWork()

    /*
      This is used to demonstrate declaring a class that does the work for you
     */
    val loggedService = new LoggedService()
    loggedService.doWork()
  }

}
