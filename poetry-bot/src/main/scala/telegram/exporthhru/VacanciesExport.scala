package telegram.exporthhru

import telegram.vacancieshhru.R00t

object VacanciesExport {
  def update(response: R00t): Unit = {

  }

  def calcNQueensCombinations(sz: Int): Int = {
    type Queen = (Int, Int)
    type Solutions = List[List[Queen]]

    def isAttacked(q1: Queen, q2: Queen) =
      q1._1 == q2._1 || q1._2 == q2._2 || (q2._1 - q1._1).abs == (q2._2 - q1._2).abs

    def isSafe(queen: Queen, others: List[Queen]) =
      others forall (!isAttacked(queen, _))

    def placeQueens(n: Int): Solutions = n match {
      case 0 => List(Nil)
      case _ => for {
        others <- placeQueens(n -1)
        y <- 1 to sz
        queen = (n, y)
        if isSafe(queen, others)
      } yield queen :: others
    }
    val solutions = placeQueens(sz)
    val result = solutions.size
    println(result + " solutions found")
    for (queen <- solutions.head; x <- 1 to sz) {
      if (queen._2 == x) print("Q ") else print(". ")
      if (x == sz) println()
    }
    result
  }
}
