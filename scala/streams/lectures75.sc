object lectures75
{

  class Pouring(capacity: Vector[Int])
  {
     // States
     type State = Vector[Int]
     val initialState = capacity map (x=>0)
     
     // Moves
     trait Move
     {
       def change(state: State): State
     }
     case class Empty(glass: Int) extends Move
     {
       def change(state: State) = state updated (glass,0)
     }
     case class Fill(glass: Int) extends Move
     {
       def change(state: State) = state updated (glass,capacity(glass))
     }
     case class Pour(from: Int, to: Int) extends Move
     {
       def change(state: State) =
       {
         val amount = state(from) min capacity(to)-state(to)
         state updated (from,state(from)-amount) updated (to,state(to)+amount)
       }
     }
     
     val glasses = 0 until capacity.length
     val moves =
       (for (g<-glasses) yield Empty(g)) ++
       (for (g<-glasses) yield Fill(g)) ++
       (for (from<-glasses; to<-glasses if from!=to) yield Pour(from,to))
      
      
  // Paths
  
    class Path(history:List[Move], val endState: State)
    {
      //(2)def endState: State = (history foldRight initialState)(_ change _) //(1) trackState(history)
      private def trackState(xs:List[Move]): State = xs match
      {
        case Nil => initialState
        case move::xs1 => move change trackState(xs1)
      }
      
      def extend (move: Move) = new Path(move::history, move change endState)
      override def toString = (history.reverse mkString " ") + " --> " + endState
    }
     
     
    val initialPath = new Path(Nil,initialState)
  
    def from (paths: Set[Path], explored: Set[State]): Stream[Set[Path]] =
      if (paths.isEmpty) Stream.empty
      else
      {
        val more = for
        {
          path <- paths
          next <- moves map path.extend
          if !(explored contains next.endState)
        } yield next
        
        paths #:: from(more, explored ++ (more map (_.endState)))
      }
      
    val pathSets = from(Set(initialPath),Set(initialState))
     
    def solutions(target: Int): Stream[Path] =
      for
      {
        pathSet <- pathSets
        path <- pathSet
        if path.endState contains target
      } yield path
  }



  val problem = new Pouring(Vector(4,9,19))       //> problem  : lectures75.Pouring = lectures75$Pouring@20b9b538
  //problem.moves

  //problem.pathSets.take(3).toList
    problem.solutions(17)                         //> res0: Stream[lectures75.problem.Path] = Stream(Fill(0) Pour(0,2) Fill(0) Fi
                                                  //| ll(1) Pour(0,2) Pour(1,2) --> Vector(0, 0, 17), ?)
}