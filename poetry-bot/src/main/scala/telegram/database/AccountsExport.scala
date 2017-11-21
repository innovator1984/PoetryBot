package telegram.database

object AccountsExport {
  import slick.jdbc.PostgresProfile.api._

  private case class Accounts(deleted: Long, id: Option[Int], key: String, tel: String, acc: String)

  private class TableAccounts(tag: Tag) extends Table[Accounts](tag, Some("public"), "accounts") {
    def deleted = column[Long]("deleted")
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def key = column[String]("key")
    def tel = column[String]("tel", O.Default("N/A"))
    def acc = column[String]("acc", O.Default("N/A"))
    override def * = (deleted, id.?, key, tel, acc) <> (Accounts.tupled, Accounts.unapply)
    def idx = index("idx_accounts_key", key, unique = false)
    def idx2 = index("idx2_accounts_deleted", deleted, unique = false)
  }
  private object TableAccounts { def table = TableQuery[TableAccounts] }

  import scala.concurrent.Await
  import scala.concurrent.duration.Duration
  import scala.concurrent.ExecutionContext.Implicits.global // for future processing
  private def saveAccountsToDB(data: Stream[Accounts], tableQuery: TableQuery[TableAccounts],
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
        db.run(tableQuery.map(x => x.deleted).update(0).transactionally)
      } else {
        Future(0)
      }
    }
    val updateTableIfExist: Future[Int] = db.run(MTable.getTables).flatMap(updateTableIfNeeded)
    Await.result(updateTableIfExist, Duration.Inf)
    val actions = data.map { dcs =>
      tableQuery.filter(cr =>
        cr.key === dcs.key
      ).exists.result.flatMap {
        case false => tableQuery += dcs
        case true => DBIO.successful()
      }
    }
    Await.result(db.run(DBIO.seq(actions: _*).transactionally), Duration.Inf)
  } finally db.close

  private def selectAccountsToDB(names: Stream[String], tableQuery: TableQuery[TableAccounts],
                                 db: Database = Database.forConfig("db.default")): String = {
    import scala.concurrent.Future
    import slick.jdbc.meta.MTable
    def selectTableIfNeeded(tables: Vector[MTable]): Future[String] = {
      if (tables.exists(_.name.name == tableQuery.baseTableRow.tableName)) {
        val q1 = for(c <- tableQuery) yield c.acc
        Future(db.stream(q1.result).toString)
      } else {
        Future("")
      }
    }
    val selectTableIfExist: Future[String] = db.run(MTable.getTables).flatMap(selectTableIfNeeded)
    Await.result(selectTableIfExist, Duration.Inf)
  }

  def search(names: Seq[String]): String = {
    selectAccountsToDB(names.toStream, TableAccounts.table)
  }

  def update(names: Seq[String]): Unit = {
    def readResponse(s: String, telPart: String, accPart: String): Stream[Accounts] = {
      println("ACC: " + s.length)
      (0 until 1).map(_ => Accounts(0, None, s, telPart, accPart)).toStream
    }
    import java.text.SimpleDateFormat
    import java.util.Calendar
    val format = new SimpleDateFormat("yyyy-MMdd-HHmm")
    val accPart = format.format(Calendar.getInstance().getTime)
    val rows = names.toStream.flatMap(s => readResponse(s, "798765432" + accPart.drop(12), accPart + "-1234"))
    saveAccountsToDB(rows, TableAccounts.table)
  }
}
