package processing

import com._
import processing.core._
import math._

object Draw extends PApplet {

  private var test:Draw = _

  def main(args: Array[String]) = {
    test = new Draw
    val frame = new javax.swing.JFrame("Test")
    frame.getContentPane.add(test)
    test.init()
    frame.pack()
    frame.setVisible(true)
  }
}

class Draw extends PApplet {

  /*
   * Substitution rules for plant-type axial tree using Node replacement.
   */
  val rules = Map (
    'X' -> "F[+X][-X]FX",
    'F' -> "FF"
  )

  /* Branching L-System using Axial Node Replacement */
  val plant = new AxialNode('X'::Nil, rules, (Pi/8).toFloat)

  val kochCurve = new Koch('F'::Nil, Map( 'F' -> "F+F-F-F+F"), (Pi/2).toFloat)

  var h = 5f //Line length

  /**
   * Set up drawing environment.
   */
  override def setup() = {
    size(400, 800)
    noLoop()
  }

  /**
   *standard override of PApplet draw loop
   */
  override def draw() = {
    background(0)
    stroke(255)
    strokeWeight(2)
    fill(250, 230, 220)
    translate(width/2, height)
    //rotate((Pi/2).toFloat)
    //line(0,0,0,-120)
    drawLSystem(plant, 7)
  }
  /**
   * Expands an L-System to a specified generation
   * @param steps the number of times to evolve the L-System
   * @param lsys the L-System to evolve
   * @return
   */
  def expandLSystem(steps: Int, lsys: LSystem): LSystem = {

    def loop(n: Int, tol: Int,  lsys: LSystem): LSystem = {
      if (n >= tol) lsys
      else loop(n + 1, tol, lsys.next)
    }

    loop(0, steps, lsys)
  }

  /**
   * draws a line of length h along the y-axis and moves
   * the frame of reference along the y-axis by h.
   */
  def drawForward(): Unit = {
    line(0,0,0,-h)
    translate(0, -h)
  }

  /**
   * moves the frame of reference along the y-axis by h.
   */
  def skipForward() = {
    translate(0, -h)
  }

  /**
   * Interprets the symbolic representation of an L-System and
   * draws the L-System according to the standard rules.
   * @param l L-System to be interpreted
   * @param steps the recursion depth of the L-System
   */
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

  /**
   * Specifies rules for drawing L-Systems of the Koch type.
   * @param k Koch type L-System to be drawn.
   */
  def drawKoch(k: Koch) = {

    val p = k.phase

    val symRules: Map[Char, () => Unit] =
      Map(
        'F' -> {() => drawForward()},
        'f' -> {() => skipForward()}
      )

    val conRules: Map[Char, () => Unit] =
      Map(
        '+' -> {() => rotate(-p)},
        '-' -> {() => rotate(p)}
      )

    applyDrawRules(k, symRules, conRules)

  }

  /**
   * Specifies rules for drawing L-Systems that use Edge Replacement
   * @param e Edge Replacing L-System to be drawn.
   */
  def drawEdge(e: Edge) = {
    val p = e.phase

    val symRules: Map[Char, () => Unit] =
      Map(
        'X' -> {() => drawForward()},
        'Y' -> {() => drawForward()}
      )
    val conRules: Map[Char, () => Unit] =
      Map(
        '+' -> {() => rotate(-p)},
        '-' -> {() => rotate(p)}
      )

    applyDrawRules(e, symRules, conRules)
  }

  /**
   * Specifies rules for drawing L-Systems that use Node Replacement
   * @param n Node Replacing L-System to be drawn.
   */
  def drawNode(n: Node) = {
    val p = n.phase

    val symRules: Map[Char, () => Unit] =
      Map(
        //do nothing...
        'X' -> {() => ()},
        'Y' -> {() => ()}
      )

    val conRules: Map[Char, () => Unit] =
      Map(
        'F' -> {() => drawForward()},
        '+' -> {() => rotate(-p)},
        '-' -> {() => rotate(p)}
      )

    applyDrawRules(n, symRules, conRules)
  }

  /**
   * Specifies rules for drawing  branching L-Systems that use
   * Edge Replacement.
   * @param ae Axial Edge Replacing L-System to be drawn.
   */
  def drawAxialEdge(ae: AxialEdge) = {

    val p = ae.phase

    val symRules: Map[Char, () => Unit] =
      Map(
        'F' -> {() => drawForward()}
      )

    val conRules: Map[Char, () => Unit] =
      Map(
        '+' -> {() => rotate(-p)},
        '-' -> {() =>rotate(p)},
        '[' -> {() => pushMatrix()},
        ']' -> {() => popMatrix()}
      )

    applyDrawRules(ae, symRules, conRules)

  }

  /**
   * Specifies rules for drawing branching L-Systems that use
   * Node Replacement
   * @param an the Axial Node Replacing L-System to be drawn.
   */
  def drawAxialNode(an: AxialNode) = {
    val p = an.phase

    val symRules: Map[Char, () => Unit] =
      Map(
        'X' -> {() => ()},
        'F' -> {() => drawForward()}
      )

    val conRules: Map[Char, () => Unit] =
      Map(
        '+' -> {() => rotate(p)},
        '-' -> {() => rotate(-p)},
        '[' -> {() => pushMatrix()},
        ']' -> {() => popMatrix()}
      )

    applyDrawRules(an, symRules, conRules)
  }

  /**
   * Applies the drawing rules for each character of the symbolic representation of the L-System.
   * Seperated rule mappings for both Replaceable and Constant symbols are specified in separate maps:
   * symRules and conRules, respectively.
   * @param l the L-System to be interpreted
   * @param symRules mapping of function calls to be applied for each character of the Set of replaceable symbols
   * @param conRules mapping of function calls to be applied for each character of the Set of constant symbols
   */
  def applyDrawRules(l: LSystem, symRules: Map[Char, () => Unit], conRules:Map[Char, () => Unit]): Unit = {

    val symbols = l.sym

    val constants = l.C

    def loop(symbols: List[Char]): Unit = symbols match {
      case Nil => Unit
      case x::xs => 
        if (constants contains x) {
          //Grab the function call from the map and apply
          conRules(x)()
          //continue
          loop(xs)
        }
        else {
          //Grab the function call from the map and apply
          symRules(x)()
          //continue
          loop(xs)
        }
    }

    loop(symbols)

  }
}
