package forcomp
import common._

object scratch
{
   List("Every", "student", "likes", "scala").groupBy((element: String) => element.length)
                                                  //> res0: scala.collection.immutable.Map[Int,List[java.lang.String]] = Map(5 -> 
                                                  //| List(Every, likes, scala), 7 -> List(student))
   List (0, 1, 2, 1, 0) groupBy ((elem: Int)=>elem)
                                                  //> res1: scala.collection.immutable.Map[Int,List[Int]] = Map(1 -> List(1, 1), 2
                                                  //|  -> List(2), 0 -> List(0, 0))

  type Word = String
  type Sentence = List[Word]
  type Occurrences = List[(Char, Int)]
  def wordOccurrences(w: Word): Occurrences =
  {
     val chrGroups = w.toLowerCase groupBy identity
     val chrGrpCounted = chrGroups map {case (chr,aggregatedChrs) => (chr,aggregatedChrs.length)}
     chrGrpCounted.toList.sortWith( _._1 < _._1 )
  }                                               //> wordOccurrences: (w: forcomp.scratch.Word)forcomp.scratch.Occurrences
  def sentenceOccurrences(s: Sentence): Occurrences =
  {
    val concatenated = s mkString ""
    wordOccurrences (concatenated)
  }                                               //> sentenceOccurrences: (s: forcomp.scratch.Sentence)forcomp.scratch.Occurrence
                                                  //| s

  def combinations(occurrences: Occurrences): List[Occurrences] =
  {
    // kind of ugly flip back to string to make use of built in combinations
    val charList = for {
    (chr,len) <- occurrences
    } yield chr.toString * len
    val word = charList mkString ""
  
    val combs = for {
      substrLength <- 1 to word.length
      substring <- word.combinations(substrLength).toList
    } yield wordOccurrences(substring)
  
    combs.toList
  }                                               //> combinations: (occurrences: forcomp.scratch.Occurrences)List[forcomp.scratc
                                                  //| h.Occurrences]

  
   /**
    * this works:
    *
    (occurrences foldRight List[Occurrences](Nil)) { case((ch,tm),acc) =>
    {
      acc ::: ( for { comb<-acc; n<-1 to tm } yield (ch,n) :: comb )
    }
    }
   */
  
  val abbaStr = "abba"                            //> abbaStr  : java.lang.String = abba
  val abba = wordOccurrences(abbaStr)             //> abba  : forcomp.scratch.Occurrences = List((a,2), (b,2))
  
  val w1 = for {
    (chr,len) <- abba
  } yield chr.toString * len                      //> w1  : List[String] = List(aa, bb)
  
  val w2 = w1 mkString ""                         //> w2  : String = aabb
  
  
  (for {
    substrLength <- 1 to w2.length
    substring <- w2.combinations(substrLength).toList
  } yield wordOccurrences(substring) )            //> res2: scala.collection.immutable.IndexedSeq[forcomp.scratch.Occurrences] = 
                                                  //| Vector(List((a,1)), List((b,1)), List((a,2)), List((a,1), (b,1)), List((b,2
                                                  //| )), List((a,2), (b,1)), List((a,1), (b,2)), List((a,2), (b,2)))
  
  
  
  
  //combinations(abba)
  def subtract(x: Occurrences, y: Occurrences): Occurrences =
  {
    def subTerm(initialList: Map[Char,Int], term: (Char, Int)): Map[Char,Int] =
    {
      val (chr,count) = term
      val newCount = initialList(chr) - count
      if (newCount==0 )
        initialList - chr
      else
        initialList updated (chr, newCount)
    }
  
    ((y.toMap foldLeft x.toMap)(subTerm)).toList.sortWith( _._1 < _._1 )
  }                                               //> subtract: (x: forcomp.scratch.Occurrences, y: forcomp.scratch.Occurrences)f
                                                  //| orcomp.scratch.Occurrences

  //val lard = List(('a',1),('d',1),('l',1),('r',1))
  //val r = List(('r',1))
  //val lad = List(('a',1),('d',1),('l',1))
  //subtract(lard, r)

  //val a2 = wordOccurrences("assessment")
  //val a3 = wordOccurrences("assess")
  //subtract (a2, a3)
  
  
  
  
  val dictionary: List[Word] = loadDictionary     //> dictionary  : List[forcomp.scratch.Word] = List(Aarhus, Aaron, Ababa, aback
                                                  //| , abaft, abandon, abandoned, abandoning, abandonment, abandons, abase, abas
                                                  //| ed, abasement, abasements, abases, abash, abashed, abashes, abashing, abasi
                                                  //| ng, abate, abated, abatement, abatements, abater, abates, abating, Abba, ab
                                                  //| be, abbey, abbeys, abbot, abbots, Abbott, abbreviate, abbreviated, abbrevia
                                                  //| tes, abbreviating, abbreviation, abbreviations, Abby, abdomen, abdomens, ab
                                                  //| dominal, abduct, abducted, abduction, abductions, abductor, abductors, abdu
                                                  //| cts, Abe, abed, Abel, Abelian, Abelson, Aberdeen, Abernathy, aberrant, aber
                                                  //| ration, aberrations, abet, abets, abetted, abetter, abetting, abeyance, abh
                                                  //| or, abhorred, abhorrent, abhorrer, abhorring, abhors, abide, abided, abides
                                                  //| , abiding, Abidjan, Abigail, Abilene, abilities, ability, abject, abjection
                                                  //| , abjections, abjectly, abjectness, abjure, abjured, abjures, abjuring, abl
                                                  //| ate, ablated, ablates, 
                                                  //| Output exceeds cutoff limit.
  lazy val dictionaryByOccurrences: Map[Occurrences, List[Word]] = dictionary groupBy wordOccurrences withDefaultValue List()
                                                  //> dictionaryByOccurrences  : Map[forcomp.scratch.Occurrences,List[forcomp.scr
                                                  //| atch.Word]] = <lazy>
  def iter0 (occurrences: Occurrences): List[Sentence] =
  {
    if (occurrences.isEmpty) List(List())  // list of empty list
    else
      for
      {
        combo <- combinations(occurrences)			// get all combinations for this occurence list
        word <- dictionaryByOccurrences(combo)	// get all words that have this combination of occurences
        // cycle round , removing this word's occurence list to get the "rest" (in lecture 6.7 parlance)
        sentence <- iter0 (subtract(occurrences,wordOccurrences(word)))	// cycle round, removi
      } yield word :: sentence
  }                                               //> iter0: (occurrences: forcomp.scratch.Occurrences)List[forcomp.scratch.Sente
                                                  //| nce]
  
  //iter0 (List())
  
  val s1 = List("Bruce", "J", "Horner")           //> s1  : List[java.lang.String] = List(Bruce, J, Horner)
  val o1 = sentenceOccurrences(s1)                //> o1  : forcomp.scratch.Occurrences = List((b,1), (c,1), (e,2), (h,1), (j,1),
                                                  //|  (n,1), (o,1), (r,3), (u,1))
  //val c1 = for ( c<-combinations(o1) ) yield c
  //val wd1 = for ( c<-combinations(o1); w<-dictionaryByOccurrences(c) ) yield w
  
  val ana1 = iter0 (o1)                           //> ana1  : List[forcomp.scratch.Sentence] = List(List(be, Jo, err, churn), Lis
                                                  //| t(be, Jo, churn, err), List(be, err, Jo, churn), List(be, err, churn, Jo), 
                                                  //| List(be, churn, Jo, err), List(be, churn, err, Jo), List(he, Jo, Burr, CERN
                                                  //| ), List(he, Jo, CERN, Burr), List(he, err, Jon, curb), List(he, err, curb, 
                                                  //| Jon), List(he, Jon, err, curb), List(he, Jon, curb, err), List(he, curb, er
                                                  //| r, Jon), List(he, curb, Jon, err), List(he, Burr, Jo, CERN), List(he, Burr,
                                                  //|  CERN, Jo), List(he, CERN, Jo, Burr), List(he, CERN, Burr, Jo), List(en, Jo
                                                  //| , err, Burch), List(en, Jo, curb, Herr), List(en, Jo, Herr, curb), List(en,
                                                  //|  Jo, Burch, err), List(en, err, Jo, Burch), List(en, err, Burch, Jo), List(
                                                  //| en, curb, Jo, Herr), List(en, curb, Herr, Jo), List(en, Herr, Jo, curb), Li
                                                  //| st(en, Herr, curb, Jo), List(en, Burch, Jo, err), List(en, Burch, err, Jo),
                                                  //|  List(re, re, Jo, brunch), List(re, re, job, churn), List(re, re, Jon, Burc
                                                  //| h), List(re, re, curb, 
                                                  //| Output exceeds cutoff limit.
   ana1 filter (x => x.length==2)                 //> res3: List[forcomp.scratch.Sentence] = List(List(herb, conjurer), List(conj
                                                  //| urer, herb))
}