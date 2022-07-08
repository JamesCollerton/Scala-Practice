package org.example.application
package futures

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}

object BasicFuturesExample {

  def main(args: Array[String]): Unit = {
    simpleFutureExample()
    callbackExample()
  }

  def simpleFutureExample(): Unit = {

    {
      import scala.concurrent.ExecutionContext.Implicits.global

      /*
        Here we are mapping the numbers 0 to 9 into futures executing in the global execution context (which I assume
        is a thread pool).
       */
      val futures: Seq[Future[String]] = (0 to 9) map { //
        i =>
          /*
            The Future class has an implicit second argument, which we've imported to use the global thread pool (hence
            the import statement above).

            Note, each of these futures will be executed ASAP, so there's no guarantee of being in order
           */
          Future {
            val s = i.toString //
            print(s)
            s
          }
      }

      /*
        Note at this point we collect the futures into a single future, which is non-blocking. It's only at the next
        stage that we block things as we need to get the result.

        This stage will be executed in order (0,1,2,3...) as we are reducing left, so we need to wait
       */
      val f: Future[String] = Future.reduceLeft(futures)((s1, s2) => s1 + s2)

      /*
        Here is the blocking step
       */
      val n: String = Await.result(f, Duration.Inf)

      println()
      println(n)
    }
  }

  def callbackExample(): Unit = {

    {
      import scala.concurrent.Future
      import scala.concurrent.ExecutionContext.Implicits.global

      /*
        Here we declare a simple class extending a runtime exception for when we
        get odd numbers
     */
      case class ThatsOdd(i: Int) extends RuntimeException(s"odd $i received!")

      import scala.util.{Try, Success, Failure}

      val doComplete: PartialFunction[Try[String],Unit] = {
        case s @ Success(_) => println(s) //
        case f @ Failure(_) => println(f)
      }

      val futures: Seq[Future[String]] = (0 to 9) map { //
        case i if i % 2 == 0 => Future.successful(i.toString)
        case i => Future.failed(ThatsOdd(i))
      }

      futures map (f => Await.ready(f andThen doComplete, Duration.Inf))

    }
  }

}
