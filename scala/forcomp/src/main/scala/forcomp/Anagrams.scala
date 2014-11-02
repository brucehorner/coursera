package forcomp

import common._

object Anagrams {

  /** A word is simply a `String`. */
  type Word = String

  /** A sentence is a `List` of words. */
  type Sentence = List[Word]

  /** `Occurrences` is a `List` of pairs of characters and positive integers saying
   *  how often the character appears.
   *  This list is sorted alphabetically w.r.t. to the character in each pair.
   *  All characters in the occurrence list are lowercase.
   *  
   *  Any list of pairs of lowercase characters and their frequency which is not sorted
   *  is **not** an occurrence list.
   *  
   *  Note: If the frequency of some character is zero, then that character should not be
   *  in the list.
   */
  type Occurrences = List[(Char, Int)]

  /** The dictionary is simply a sequence of words.
   *  It is predefined and obtained as a sequence using the utility method `loadDictionary`.
   */
  val dictionary: List[Word] = loadDictionary

  /** Converts the word into its character occurence list.
   *  
   *  Note: the uppercase and lowercase version of the character are treated as the
   *  same character, and are represented as a lowercase character in the occurrence list.
   */
  def wordOccurrences(w: Word): Occurrences = 
  {
    // group letters together, will form string of repeating characters as value to the character key
    val chrGroups = w.toLowerCase groupBy identity
    
    // so then convert to a map of character and the length of that string
    val chrGrpCounted = chrGroups map {case (chr,aggregatedChrs) => (chr,aggregatedChrs.length)}
    
    // finally convert to a list and sort by the character, which is the first part of the pair, the key
    chrGrpCounted.toList.sortWith( _._1 < _._1 )
  }
  
  /** Converts a sentence into its character occurrence list. */
  // just collapse all words of the sentence into one long string and count its occurrences
  def sentenceOccurrences(s: Sentence): Occurrences = wordOccurrences(s mkString "")
  
  /** The `dictionaryByOccurrences` is a `Map` from different occurrences to a sequence of all
   *  the words that have that occurrence count.
   *  This map serves as an easy way to obtain all the anagrams of a word given its occurrence list.
   *  
   *  For example, the word "eat" has the following character occurrence list:
   *
   *     `List(('a', 1), ('e', 1), ('t', 1))`
   *
   *  Incidentally, so do the words "ate" and "tea".
   *
   *  This means that the `dictionaryByOccurrences` map will contain an entry:
   *
   *    List(('a', 1), ('e', 1), ('t', 1)) -> Seq("ate", "eat", "tea")
   *
   */
  lazy val dictionaryByOccurrences: Map[Occurrences, List[Word]] = 
    dictionary groupBy wordOccurrences withDefaultValue List()		// for missing keys, return empty List

  /** Returns all the anagrams of a given word. */
  // look up the new dictionary using the occurrence list of the word
  def wordAnagrams(word: Word): List[Word] = dictionaryByOccurrences(wordOccurrences(word))

  /** Returns the list of all subsets of the occurrence list.
   *  This includes the occurrence itself, i.e. `List(('k', 1), ('o', 1))`
   *  is a subset of `List(('k', 1), ('o', 1))`.
   *  It also include the empty subset `List()`.
   * 
   *  Example: the subsets of the occurrence list `List(('a', 2), ('b', 2))` are:
   *
   *    List(
   *      List(),
   *      List(('a', 1)),
   *      List(('a', 2)),
   *      List(('b', 1)),
   *      List(('a', 1), ('b', 1)),
   *      List(('a', 2), ('b', 1)),
   *      List(('b', 2)),
   *      List(('a', 1), ('b', 2)),
   *      List(('a', 2), ('b', 2))
   *    )
   *
   *  Note that the order of the occurrence list subsets does not matter -- the subsets
   *  in the example above could have been displayed in some other order.
   */
  def combinations(occurrences: Occurrences): List[Occurrences] = 
  {
    // kind of ugly flip back to string to make use of built in String.combinations
    // make substrings with the character multiplied out by the occurrence length
    val charList = for ( (chr,len) <- occurrences ) yield chr.toString * len
    val word = charList mkString ""
  
    // now use the combinations of the string for every sub-string inside it and 
    // build up the sequence of occurrences for each substring
    val combs = for
    {
      substrLength <- 1 to word.length
      substring <- word.combinations(substrLength).toList
    } yield wordOccurrences(substring)
  
    // also a bit tacky, but add the empty list to the converted combinations  
    List() :: combs.toList
  }
  
  /** Subtracts occurrence list `y` from occurrence list `x`.
   * 
   *  The precondition is that the occurrence list `y` is a subset of
   *  the occurrence list `x` -- any character appearing in `y` must
   *  appear in `x`, and its frequency in `y` must be smaller or equal
   *  than its frequency in `x`.
   *
   *  Note: the resulting value is an occurrence - meaning it is sorted
   *  and has no zero-entries.
   */
  def subtract(x: Occurrences, y: Occurrences): Occurrences =
  {
    // copy approach from Poly in the lectures, removing now rather than
    // adding as in the lectures.  assumption is that term key is in the 
    // initialList.  Grab the values from the term, reducing amount per the value
    // of the term.  If the new count is zero, completely remove the entry from the 
    // map, otherwise update it to contain the new character count
    def subTerm(initialList: Map[Char,Int], term: (Char, Int)): Map[Char,Int] =
    {
      val (chr,count) = term
      val newCount = initialList(chr) - count
      if (newCount==0 )
        initialList - chr
      else
        initialList updated (chr, newCount)
    }
  
    // collapse the smaller list into the larger one, after converting both to maps
    // using the subtract auxilliary function
    // then convert and sort the final list in character order
    ((y.toMap foldLeft x.toMap)(subTerm)).toList.sortWith( _._1 < _._1 )    
    
  }

  /** Returns a list of all anagram sentences of the given sentence.
   *  
   *  An anagram of a sentence is formed by taking the occurrences of all the characters of
   *  all the words in the sentence, and producing all possible combinations of words with those characters,
   *  such that the words have to be from the dictionary.
   *
   *  The number of words in the sentence and its anagrams does not have to correspond.
   *  For example, the sentence `List("I", "love", "you")` is an anagram of the sentence `List("You", "olive")`.
   *
   *  Also, two sentences with the same words but in a different order are considered two different anagrams.
   *  For example, sentences `List("You", "olive")` and `List("olive", "you")` are different anagrams of
   *  `List("I", "love", "you")`.
   *  
   *  Here is a full example of a sentence `List("Yes", "man")` and its anagrams for our dictionary:
   *
   *    List(
   *      List(en, as, my),
   *      List(en, my, as),
   *      List(man, yes),
   *      List(men, say),
   *      List(as, en, my),
   *      List(as, my, en),
   *      List(sane, my),
   *      List(Sean, my),
   *      List(my, en, as),
   *      List(my, as, en),
   *      List(my, sane),
   *      List(my, Sean),
   *      List(say, men),
   *      List(yes, man)
   *    )
   *
   *  The different sentences do not have to be output in the order shown above - any order is fine as long as
   *  all the anagrams are there. Every returned word has to exist in the dictionary.
   *  
   *  Note: in case that the words of the sentence are in the dictionary, then the sentence is the anagram of itself,
   *  so it has to be returned in this list.
   *
   *  Note: There is only one anagram of an empty sentence.
   */
  def sentenceAnagrams(sentence: Sentence): List[Sentence] =
  {
	  def findWordsAndSentences (occurrences: Occurrences): List[Sentence] =
	  {
	    if (occurrences.isEmpty)
	      List(Nil)  									// list of empty list for an empty occurence list
	    else
	      for
	      {
	        combo <- combinations(occurrences)			// get all combinations for this occurrence list
	        word <- dictionaryByOccurrences(combo)		// get all words that have this combination of occurrences
	        // cycle round, removing this word's occurrence list to get the "rest" (in lecture 6.7 parlance) as 
	        // a sentence, so the expression can yield this word with this sentence
	        sentence <- findWordsAndSentences (subtract(occurrences,wordOccurrences(word)))
	      } yield word :: sentence
	  }     
    
    findWordsAndSentences (sentenceOccurrences(sentence))
  }

}
