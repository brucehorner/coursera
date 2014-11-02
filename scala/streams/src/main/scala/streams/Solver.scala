package streams

import common._

/**
 * This component implements the solver for the Bloxorz game
 */
trait Solver extends GameDef {

  /**
   * Returns `true` if the block `b` is at the final position
   */
  def done(b: Block): Boolean =
    b.b1==goal && b.b2==goal				// both parts of the block are at the goal

  /**
   * This function takes two arguments: the current block `b` and
   * a list of moves `history` that was required to reach the
   * position of `b`.
   * 
   * The `head` element of the `history` list is the latest move
   * that was executed, i.e. the last move that was performed for
   * the block to end up at position `b`.
   * 
   * The function returns a stream of pairs: the first element of
   * the each pair is a neighboring block, and the second element
   * is the augmented history of moves required to reach this block.
   * 
   * It should only return valid neighbors, i.e. block positions
   * that are inside the terrain.
   */
  def neighborsWithHistory(b: Block, history: List[Move]): Stream[(Block, List[Move])] =
    ( for ( (newBlock,move)<-b.legalNeighbors ) yield (newBlock,move::history) ).toStream

  /**
   * This function returns the list of neighbors without the block
   * positions that have already been explored. We will use it to
   * make sure that we don't explore circular paths.
   */
  def newNeighborsOnly(neighbors: Stream[(Block, List[Move])],
                       explored: Set[Block]): Stream[(Block, List[Move])] =
    ( for
      {
        (block,moveHistory) <- neighbors
        if !(explored contains block)
      } yield (block,moveHistory)
    ).toStream

  /**
   * The function `from` returns the stream of all possible paths
   * that can be followed, starting at the `head` of the `initial`
   * stream.
   * 
   * The blocks in the stream `initial` are sorted by ascending path
   * length: the block positions with the shortest paths (length of
   * move list) are at the head of the stream.
   * 
   * The parameter `explored` is a set of block positions that have
   * been visited before, on the path to any of the blocks in the
   * stream `initial`. When search reaches a block that has already
   * been explored before, that position should not be included a
   * second time to avoid circles.
   * 
   * The resulting stream should be sorted by ascending path length,
   * i.e. the block positions that can be reached with the fewest
   * amount of moves should appear first in the stream.
   * 
   * Note: the solution should not look at or compare the lengths
   * of different paths - the implementation should naturally
   * construct the correctly sorted stream.
   */
  def from(initial: Stream[(Block, List[Move])],
           explored: Set[Block]): Stream[(Block, List[Move])] =
  {
    if (initial.isEmpty) Stream.empty
    else
    {
      // decompose the first element on the initial stream since we're
      // doing breadth first search this will also be the first part of the
      // stream we return
      val (initialBlock,initialMoves) = initial.head
      
      // get new neighbors of current top of the set by first getting all
      // neighbors then removing ones we've visited before
      // then pull the blocks from newly found neighbors so we can add them
      // to the explored set
      val allNeighbors = neighborsWithHistory(initialBlock,initialMoves)
      val newNeighbors = newNeighborsOnly(allNeighbors,explored)
      val newBlocks = newNeighbors map { case(block,_) => block }

      // put the head of the stream first, then follow with the
	  // set built from the remainder of this stream concatenated with the
	  // new neighbors to explore and with the prior visited locations
	  // combined with the neighbors just found
      (initialBlock,initialMoves) #:: from(initial.tail #::: newNeighbors, explored ++ newBlocks)
    }
  }             

  /**
   * The stream of all paths that begin at the starting block.
   */
  lazy val pathsFromStart: Stream[(Block, List[Move])] =
  {
    val initial = Set((startBlock,List()))
    val explored = Set(startBlock)
    from (initial.toStream, explored)
  }

  /**
   * Returns a stream of all possible pairs of the goal block along
   * with the history how it was reached.
   */
  lazy val pathsToGoal: Stream[(Block, List[Move])] =
    pathsFromStart filter { case(block,moves) => done(block) }

  /**
   * The (or one of the) shortest sequence(s) of moves to reach the
   * goal. If the goal cannot be reached, the empty list is returned.
   *
   * Note: the `head` element of the returned list should represent
   * the first move that the player should perform from the starting
   * position.
   */
  lazy val solution: List[Move] =
  {
    val paths = pathsToGoal
    // deal with the boundary when there's no path
    if (paths.isEmpty) List()
    else
      // pull out the list of moves , reverse it and then take 
      // the List from inside the stream with the apply 0
      ( paths map { case(block,moves) => moves.reverse } ) apply 0
  }
}
