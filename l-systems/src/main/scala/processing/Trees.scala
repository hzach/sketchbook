package processing

import com._
import processing.core._
import math._
import scala.annotation.tailrec
import scala.util.Random

object Trees extends PApplet {

  private var test:Trees = _

  def main(args: Array[String]) = {
    test = new Trees
    val frame = new javax.swing.JFrame("Test")
    frame.getContentPane().add(test)
    test.init
    frame.pack
    frame.setVisible(true)
  }
}

class Trees extends PApplet {

  var img: PImage = new PImage()

  val rules = Map (
    'X' -> "F[+X][-X]FX",
    'F' -> "FF"
  )

  val plant = new AxialNode('X'::Nil, rules, (Pi/8).toFloat)

  val kochCurve = new Koch('F'::Nil, Map( 'F' -> "+"), (Pi/2).toFloat)

  var h = 15f

  override def setup() = {
    size(1000, 1000)
    noLoop()
    img = loadImage("/home/zach/Pictures/Doge.png")
  }


  override def draw() = {
    background(230,230,220)
    stroke(0)
    fill(250,230,220)
    translate(width/2, height/2)
    //line(0,0,0,-120)
    drawLSystem(kochCurve, 3)
  }

  def expandLSystem(steps: Int, tree: LSystem): LSystem = {

    def loop(n: Int, tol: Int, tree: LSystem): LSystem = {
      if (n >= tol) tree
      else loop(n + 1, tol, tree.next)
    }

    loop(0, steps, tree)
  }

  def drawForward(h: Float): Unit = {
    line(0,0,0,-h)
    translate(0, -h)
  }

  def skipForward = {
    translate(0, -h)
  }

  def drawLSystem(l: LSystem, steps: Int) = {

    val L = expandLSystem(steps, l)

    L match {
      case (k: Koch) => drawKoch(k)
      case (e: Edge) => drawEdge(e)
      case (n: Node) => drawNode(n)
      case (ae: AxialEdge) => drawAxialEdge(ae)
      case (an: AxialNode) => drawAxialNode(an)
    }
  }

  def drawKoch(k: Koch) = {

    val p = k.phase

    val symRules: Map[Char, () => Unit] = Map('F' -> drawForward, 'f' -> skipForward)
    val conRules = Map('+' -> rotate(-p), '-' -> rotate(p))

    applyDrawRules(k, symRules, conRules)

  }

  def drawEdge(e: Edge) = {
    val p = e.phase

    val symRules = Map('X' -> drawForward, 'Y' -> drawForward)
    val conRules = Map('+' -> rotate(-p), '-' -> rotate(p))

    applyDrawRules(e, symRules, conRules)
  }

  def drawNode(n: Node) = {
    val p = n.phase

    val symRules = Map('X' -> (), 'Y' -> ())
    val conRules = Map('F' -> drawForward, '+' -> rotate(-p), '-' -> rotate(p))

    applyDrawRules(n, symRules, conRules)
  }

  def drawAxialEdge(ae: AxialEdge) = {
    val p = ae.phase

    val symRules = Map('F' -> drawForward)
    val conRules = Map('+' -> rotate(-p), '-' -> rotate(p),'[' -> pushMatrix(), ']' -> popMatrix())

    applyDrawRules(ae, symRules, conRules)

  }

  def drawAxialNode(an: AxialNode) = {
    val p = an.phase

    val symRules = Map('X' -> {}, 'F' -> drawForward)
    val conRules = Map('+' -> rotate(p), '-' -> rotate(-p),'[' -> pushMatrix(), ']' -> popMatrix())

    applyDrawRules(an, symRules, conRules)
  }

  def applyDrawRules(l: LSystem, symRules: Map[Char, Unit], conRules: Map[Char, Unit]): Unit = {

    val symbols = l.sym

    val constants = l.C

    def loop(symbols: List[Char]): Unit = symbols match {
      case Nil => Unit
      case x::xs => {
        if (constants contains x) {
          println("constant encountered: " + x)
          println(conRules(x))
          conRules(x)
          loop(xs)
        }
        else {
          println("symbol encountered: " + x)
          println(symRules(x))
          symRules(x)
          loop(xs)
        }
      }
    }

    loop(symbols)

  }
}
