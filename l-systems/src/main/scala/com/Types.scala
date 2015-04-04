package com

/**
 * An L-System of Type Koch.
 * @param sym the symbolic representation of this system. Should be the axiom of the system of instantiation.
 * @param rules the rules governing the replacement of the symbols in the representation of this LSystem.
 * @param phase the phase of rotations represented by constant symbols.
 */
case class Koch(sym: List[Char], rules: Map[Char, String], phase: Float) extends LSystem {

  val V: Set[Char] = Set('f', 'F')

  val C: Set[Char] = Set('-','+')

  override def next: LSystem = new Koch(replace(), rules, phase)
}

/**
 * An L-System that uses the Edge-Replacement strategy.
 * @param sym the symbolic representation of this system. Should be the axiom of the system of instantiation.
 * @param rules the rules governing the replacement of the symbols in the representation of this LSystem.
 * @param phase the phase of rotations represented by constant symbols.
 */
case class Edge(sym: List[Char], rules: Map[Char, String], phase: Float) extends LSystem {

  val V: Set[Char] = Set('X', 'Y')

  val C: Set[Char] = Set('-', '+')

  override def next: LSystem = Edge(replace(), rules, phase)
}

/**
 * An L-System that uses the Node-Replacement strategy.
 * @param sym the symbolic representation of this system. Should be the axiom of the system of instantiation.
 * @param rules the rules governing the replacement of the symbols in the representation of this LSystem.
 * @param phase the phase of rotations represented by constant symbols.
 */
case class Node(sym: List[Char], rules: Map[Char, String], phase: Float) extends LSystem {

  val V: Set[Char] = Set('L', 'R')

  val C: Set[Char] = Set('F', '-', '+')

  override def next: LSystem = Node(replace(), rules, phase)
}

/**
 * An Branching L-System that uses the Edge-Replacement strategy.
 * @param sym the symbolic representation of this system. Should be the axiom of the system of instantiation.
 * @param rules the rules governing the replacement of the symbols in the representation of this LSystem.
 * @param phase the phase of rotations represented by constant symbols.
 */
case class AxialEdge(sym: List[Char], rules: Map[Char, String], phase: Float) extends LSystem {

  val V: Set[Char] = Set('F')

  val C: Set[Char] = Set('-', '+', '[', ']')

  override def next: LSystem = AxialEdge(replace(), rules, phase)
}

/**
 * An branching L-System that uses the Node-Replacement strategy.
 * @param sym the symbolic representation of this system. Should be the axiom of the system of instantiation.
 * @param rules the rules governing the replacement of the symbols in the representation of this LSystem.
 * @param phase the phase of rotations represented by constant symbols.
 */
case class AxialNode(sym: List[Char], rules: Map[Char, String], phase: Float) extends LSystem {

  val V: Set[Char] = Set('X', 'F')

  val C: Set[Char] = Set('-', '+', '[', ']')

  override def next: LSystem = AxialNode(replace(), rules, phase)
}
