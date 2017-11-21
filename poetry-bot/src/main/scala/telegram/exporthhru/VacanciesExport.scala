package telegram.exporthhru


import com.google.gson.Gson
import telegram.vacancieshhru.R00t

object VacanciesExport {
  // READ https://stackoverflow.com/q/13381153 q/33161044 q/33929709 a/21120108
  // http://slick.lightbend.com/doc/3.0.0/gettingstarted.html
  // https://stackoverflow.com/q/45889096
  // http://slick.lightbend.com/doc/3.2.0/schemas.html

  import slick.jdbc.PostgresProfile.api._

  private case class Vacancies(deleted: Long, id: Option[Int], key: String, json: String)

  private class TableVacancies(tag: Tag) extends Table[Vacancies](tag, Some("public"), "vacancies") {
    def deleted = column[Long]("deleted")
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def key = column[String]("key")
    def json = column[String]("json", O.Default("N/A"))
    override def * = (deleted, id.?, key, json) <> (Vacancies.tupled, Vacancies.unapply)
    def idx = index("idx_key", key, unique = false)
    def idx2 = index("idx2_deleted", deleted, unique = false)
  }
  private object TableVacancies { def table = TableQuery[TableVacancies] }

  import scala.concurrent.Await
  import scala.concurrent.duration.Duration
  import scala.concurrent.ExecutionContext.Implicits.global // for future processing
  private def saveVacanciesToDB(data: Stream[Vacancies], tableQuery: TableQuery[TableVacancies],
                        db: Database = Database.forConfig("db.default")): Unit = try {
    import scala.concurrent.Future
    import slick.jdbc.meta.MTable
    def createTableIfNotInTables(tables: Vector[MTable]): Future[Unit] = {
      if (!tables.exists(_.name.name == tableQuery.baseTableRow.tableName)) {
        db.run(tableQuery.schema.create)
      } else {
        Future()
      }
    }
    val createTableIfNotExist: Future[Unit] = db.run(MTable.getTables).flatMap(createTableIfNotInTables)
    Await.result(createTableIfNotExist, Duration.Inf)
    def updateTableIfNeeded(tables: Vector[MTable]): Future[Int] = {
      if (tables.exists(_.name.name == tableQuery.baseTableRow.tableName)) {
        db.run(tableQuery.map(x => x.deleted).update(1).transactionally)
      } else {
        Future(0)
      }
    }
    val updateTableIfExist: Future[Int] = db.run(MTable.getTables).flatMap(updateTableIfNeeded)
    Await.result(updateTableIfExist, Duration.Inf)
    val actions = data.map { dcs =>
      tableQuery.filter(cr =>
        cr.id === dcs.id
      ).exists.result.flatMap {
        case false => tableQuery += dcs
        case true => DBIO.successful()
      }
    }
    Await.result(db.run(DBIO.seq(actions: _*).transactionally), Duration.Inf)
  } finally db.close

  def update(responses: Seq[R00t]): Unit = {
    def readResponse(s: R00t, keyPart: String): Stream[Vacancies] = {
      val gson = new Gson
      val str = gson.toJson(s)
      println("JSON: " + str.length)
      (0 until 1).map(_ => Vacancies(0, None, keyPart, json = str)).toStream
    }
    import java.text.SimpleDateFormat
    import java.util.Calendar
    val format = new SimpleDateFormat("yyyy-MM-dd_HH:mm")
    val keyPart = format.format(Calendar.getInstance().getTime)
    val rows = responses.toStream.flatMap(s => readResponse(s, keyPart))
    saveVacanciesToDB(rows, TableVacancies.table)
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
