package streams

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import Bloxorz._

@RunWith(classOf[JUnitRunner])
class BloxorzSuite extends FunSuite {

  trait SolutionChecker extends GameDef with Solver with StringParserTerrain {
    /**
     * This method applies a list of moves `ls` to the block at position
     * `startPos`. This can be used to verify if a certain list of moves
     * is a valid solution, i.e. leads to the goal.
     */
    def solve(ls: List[Move]): Block =
      ls.foldLeft(startBlock) { case (block, move) => move match {
        case Left => block.left
        case Right => block.right
        case Up => block.up
        case Down => block.down
      }
    }
  }

  trait Level1 extends SolutionChecker {
      /* terrain for level 1*/

    val level =
    """ooo-------
      |oSoooo----
      |ooooooooo-
      |-ooooooooo
      |-----ooToo
      |------ooo-""".stripMargin

    val optsolution = List(Right, Right, Down, Right, Right, Right, Down)
    
    val b1 = Block(Pos(1,2),Pos(1,3))
    val m1 = List(Right,Left,Up)
    val b2 = Block(Pos(2,1),Pos(3,1))
    val m2 = List(Down,Left,Up)
    val b3 = Block(Pos(1,1),Pos(1,1))
    val neighbors = Set( (b1, m1), (b2, m2) ).toStream
    val explored = Set(b1, b3) 
  }

  test("terrain function level 1") {
    new Level1 {
      assert(terrain(Pos(0,0)), "0,0")
      assert(!terrain(Pos(4,11)), "4,11")
    }
  }

  test("findChar level 1") {
    new Level1 {
      assert(startPos == Pos(1,1))
    }
  }

  test("optimal solution for level 1") {
    new Level1 {
      assert(solve(solution) == Block(goal, goal))
    }
  }

  test("optimal solution length for level 1") {
    new Level1 {
      assert(solution.length == optsolution.length)
    }
  }
  
  test("BH validate neighbours with history")
  {
    new Level1
    {
      assert (
          neighborsWithHistory(b3, List(Left,Up)).toSet ===
            Set( (b1, m1), (b2, m2) ) )
    }
  }
  
  test("BH new neighbours only")
  {
    new Level1
    {
      assert (
        newNeighborsOnly(
		  Set( (b1, m1), (b2, m2) ).toStream, explored) ===
            Set( (b2, m2) ).toStream )
    }
  }
  
}
