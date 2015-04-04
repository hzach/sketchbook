package com

/**
 * Created by zach on 4/3/15.
 */
case class Koch(val sym: List[Char], val rules: Map[Char, String], val phase: Float) extends LSystem {

  val V: Set[Char] = Set('f', 'F')

  val C: Set[Char] = Set('-','+')

  override def next: LSystem = new Koch(replace, rules, phase)
}

case class Edge(val sym: List[Char], val rules: Map[Char, String], val phase: Float) extends LSystem {

  val V: Set[Char] = Set('X', 'Y')

  val C: Set[Char] = Set('-', '+')

  override def next: LSystem = Edge(replace, rules, phase)
}

case class Node(val sym: List[Char], val rules: Map[Char, String], val phase: Float) extends LSystem {

  val V: Set[Char] = Set('L', 'R')

  val C: Set[Char] = Set('F', '-', '+')

  override def next: LSystem = Node(replace, rules, phase)
}

case class AxialEdge(val sym: List[Char], val rules: Map[Char, String], val phase: Float) extends LSystem {

  val V: Set[Char] = Set('F')

  val C: Set[Char] = Set('-', '+', '[', ']')

  override def next: LSystem = AxialEdge(replace, rules, phase)
}

case class AxialNode(val sym: List[Char], val rules: Map[Char, String], val phase: Float) extends LSystem {

  val V: Set[Char] = Set('X', 'F')

  val C: Set[Char] = Set('-', '+', '[', ']')

  override def next: LSystem = AxialNode(replace, rules, phase)
}
