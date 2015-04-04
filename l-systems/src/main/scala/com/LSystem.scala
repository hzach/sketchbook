package com

/**
 * Created by ztidwell on 3/31/2015.
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

  val phase: Float

  /**
   * Applies the production rules to a given character if it
   * is contained in the set of substitutable characters and returns the result.
   * returns the symbol unchanged if it is contained in the set of constants.
   * Throws an error if the symbol is in neither set.
   * @param symbol
   * @return the substituted symbol
   * @throws Error
   */
  def substitute(symbol: Char): List[Char] =
    if (V contains symbol) rules(symbol) toList
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

  def next: LSystem

}
