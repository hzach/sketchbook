/**
* Created by ztidwell on 3/31/2015.
*/

import com.{AxialNode, Koch}
import math._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class LSysTest extends FunSuite{

  trait TestObs{
    val kochCurve = new Koch('F'::Nil, Map( 'F' -> "F+F-F-F+F"), (Pi/2).toFloat)
    val step1 = kochCurve.next
    val step2 = step1.next

    val rules = Map (
      'X' -> "F[+X][-X]FX",
      'F' -> "FF"
    )

    val plant = new AxialNode('X'::Nil, rules, (Pi/8).toFloat)
  }

  test("Check parse") {
    new TestObs {
      assert( step1.sym === "F+F-F-F+F".toList)
      println((plant.next.next).sym.toString)
    }
  }
}
