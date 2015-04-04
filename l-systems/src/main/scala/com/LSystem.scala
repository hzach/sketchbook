package com

/**
 * Trait that describes the construction and evolution of Basic L-Systems.
 * An L-System is specified by a set of replaceable Symbols, V, and a Set of
 * constant symbols that are not replaceable, a set of grammar rules and an initial configuration
 * of elements of V union C called the initiator or axiom. The Members of V can be replaced by
 * members of V union C, and a Set of grammar rules which control
 * how replaceable symbols are substituted. the value sym represents the L-System at some
 * Generation. When a L-System is instantiated (0th generation), it is expected that sym will be
 * equal to the axiom of the L-System being represented.
 *
 * REFERENCE: http://algorithmicbotany.org/papers/abop/abop-ch1.pdf
 */
trait LSystem {

  /** Alphabet of symbols **/
  val V: Set[Char]

  /** Set of constants not affected by rules **/
  val C: Set[Char]

  /** Representation of the LSystem **/
  val sym: List[Char]

  /**Production rules for substituting Alphabet symbols.
  * Char must be a member of V and all elements of the String
  * Must be members of V union C **/
  val rules: Map[Char, String]

  /**
   * Controls the phase of constants that represent turns in the L-System.
   */
  val phase: Float

  /**
   * Applies the production rules to a given character if it
   * is contained in the set of substitutable characters and returns the result.
   * returns the symbol unchanged if it is contained in the set of constants.
   * Throws an error if the symbol is in neither set.
   * @param symbol to be replaced
   * @return the substituted symbol
   */
  def substitute(symbol: Char): List[Char] =
    if (V contains symbol) rules(symbol).toList
    else
      if (C contains symbol) symbol::Nil
      else throw new Error(symbol + " not found in the grammar. The representation contains invalid symbols")

  /**
   * Replaces all substitutable symbols in the current representation
   * @return the List of replaced symbols
   */
  def replace(): List[Char] = {

    /*Helper function to accumulate results in a tail recursive fashion*/
    def loop(symbols: List[Char], acc: List[Char]): List[Char] =
      symbols match {
      case Nil => acc
      case x::xs => loop(xs, acc++substitute(x))
    }

  loop(sym, Nil)

  }

  /**
   * Constructs the next generation of this L-System.
   * @return the next generation of this L-System.
   */
  def next: LSystem

}
