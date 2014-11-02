//package week6
//import week6._
  
  
object lectures64
{
  
  case class Book(title: String, authors: List[String])

  val books: List[Book] = List(
    Book(title = "Structure and Interpretation of Computer Programs", authors = List("Abelson, Harald", "Sussman, Gerald J.")),
    Book(title = "Introduction to Functional Programming", authors = List("Bird, Richard", "Wadler, Phil")),
    Book(title = "Effective Java", authors = List("Bloch, Joshua")),
    Book(title = "Java Puzzlers",  authors = List("Bloch, Joshua", "Gafter, Neal")),
    Book(title = "Programming in Scala", authors = List("Odersky, Martin", "Spoon, Lex", "Venners, Bill")))
                                                  //> books : List[lectures64.Book] = List(Book(Structure and Interpretation of Co
                                                  //| mputer Programs,List(Abelson, Harald, Sussman, Gerald J.)), Book(Introductio
                                                  //| n to Functional Programming,List(Bird, Richard, Wadler, Phil)), Book(Effecti
                                                  //| ve Java,List(Bloch, Joshua)), Book(Java Puzzlers,List(Bloch, Joshua, Gafter,
                                                  //|  Neal)), Book(Programming in Scala,List(Odersky, Martin, Spoon, Lex, Venners
                                                  //| , Bill)))

 // for ( b <- books ) yield b.title
 
 
}
  