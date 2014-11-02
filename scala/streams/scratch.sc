package streams
import common._

trait SolutionChecker extends GameDef with Solver with StringParserTerrain

trait LevelX extends SolutionChecker
{
    val level =
      """------
        |--So--
        |--To--
        |--oo--
        |------""".stripMargin
}

trait Level0 extends SolutionChecker
{
    val level =
      """------
        |--ST--
        |--oo--
        |--oo--
        |------""".stripMargin
}

trait Level1 extends SolutionChecker
{
    val level =
      """ooo-------
        |oSoooo----
        |ooooooooo-
        |-ooooooooo
        |-----ooToo
        |------ooo-""".stripMargin
}

trait Level3 extends SolutionChecker
{
    val level =
     """------ooooooo--
       |oooo--ooo--oo--
       |ooooooooo--oooo
       |oSoo-------ooTo
       |oooo-------oooo
       |------------ooo""".stripMargin

// Solutions include:
// List(Right, Up, Right, Right, Right, Up, Left, Down, Right, Up, Up, Right, Right, Right, Down, Down, Down, Right, Up)
// List(Up, Left, Down, Right, Up, Right, Right, Right, Right, Up, Up, Right, Right, Right, Down, Down, Down, Right, Up)
}

trait Level6 extends SolutionChecker
{
val level =
  """-----oooooo----
    |-----o--ooo----
    |-----o--ooooo--
    |Sooooo-----oooo
    |----ooo----ooTo
    |----ooo-----ooo
    |------o--oo----
    |------ooooo----
    |------ooooo----
    |-------ooo-----
    |---------------""".stripMargin

// Solutions include:
// List(Right, Right, Right, Down, Down, Right, Down, Down, Right, Down, Right, Up, Left, Left, Left, Up, Up, Left, Up,  Up, Up, Right, Right, Down, Right, Right, Up, Left, Down, Down, Right, Right, Down, Down, Right)
}



object scratch extends Level3
{
 solution                                         //> res0: List[streams.scratch.Move] = List(Right, Up, Right, Right, Right, Up,
                                                  //|  Left, Down, Right, Up, Up, Right, Right, Right, Down, Down, Down, Right, U
                                                  //| p)
 val p = pathsToGoal                              //> p  : Stream[(streams.scratch.Block, List[streams.scratch.Move])] = Stream((
                                                  //| Block(Pos(3,13),Pos(3,13)),List(Up, Right, Down, Down, Down, Right, Right, 
                                                  //| Right, Up, Up, Right, Down, Left, Up, Right, Right, Right, Up, Right)), ?)
                                                  //| 
 p.isEmpty                                        //> res1: Boolean = false
//( pathsToGoal map { case(block,moves) => moves.reverse } ) apply 0
}