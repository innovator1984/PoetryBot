package ideas

import telegram.vacancieshhru.{Item, R00t}

object Main extends Serializable {

  def initLog4j(): Unit = {
    // import org.apache.log4j.Logger
    // import org.apache.log4j.Level
    // Logger.getLogger("org").setLevel(Level.OFF)
    // Logger.getLogger("akka").setLevel(Level.OFF)
  }

  def main(args: Array[String]): Unit = {
    println("Welcome to Telegram!")
    // initLog4j()
    doOnline()
  }

  val hhruQueries: Map[String, Seq[String]] = Map("S" -> Seq("scala"), "J" -> Seq("spring"),
    "D" -> Seq("аналитик hadoop", "data scientist", "аналитик данных"),
    "R" -> Seq("руководитель проекта аналитического", "руководитель проекта аналитиков", "руководитель направления"),
    "G" -> Seq("руководитель группы аналитической", "руководитель группы аналитиков"),
    "P" -> Seq("пресейл ETL", "пресейл SAP"), "A" -> Seq("архитектор кластера"))
  // https://api.hh.ru/metro/1
  val hhruZones: Map[String, Seq[(String, String)]] = Map("A" -> Seq(("Курская", "5.71"), ("Kurskaya", "3.70"), ("Чкаловская", "10.72"),
    ("Римская", "10.113"), ("Площадь Ильича", "8.112"), ("Новогиреево", "8.88"), ("Новокосино", "8.189")),
    "B" -> Seq(("Бауманская", "3.17"), ("Сретенский бульвар", "10.175"), ("Чистые пруды", "1.143"),
      ("Тургеневская", "6.144"), ("Площадь Революции", "3.100"), ("Театральная", "2.99"), ("Охотный ряд", "1.98"),
      ("Таганская", "7.77"), ("Taganskaya", "5.76"), ("Марксистская", "8.78"),
      ("Комсомольская", "1.54"), ("Komsomolskaya", "5.55"), ("Авиамоторная", "8.1"), ("Электрозаводская", "3.161"),
      ("Пролетарская", "7.62"), ("Крестьянская", "10.63"), ("Третьяковская", "8.91"), ("Tretyakovskaya", "6.90"),
      ("Новокузнецкая", "2.89")),
    "C" -> Seq(("Электрозаводская", "3.161"), ("Семёновская", "3.130"),
      ("Трубная", "10.177"), ("Достоевская", "10.184"), ("Марьина Роща", "10.185"),
      ("Боровицкая", "9.7"), ("Арбатская", "4.11"), ("Arbatskaya", "3.5"), ("Александровский Сад", ""),
      ("Библиотека им. Ленина", "1.4"), ("Павелецкая", "5.102"), ("Добрынинская", "5.36"),
      ("Октябрьская", "6.94"), ("Oktyabrskaya", "5.93"),
      ("Проспект мира", "6.120"), ("Новослободская", "5.82"),
      ("Дубровка", "10.39"), ("Кожуховская", "10.52"), ("Перово", "95.524"), ("Шоссе Энтузиастов", "8.158")))

  def sendRequest(): Map[(String, String), Seq[R00t]] = {
      val stream0 = for {kq <- hhruQueries.keys} yield ((kq, "_"), sendOne(kq, "_"))
      val stream = for {kq <- hhruQueries.keys
                      kz <- hhruZones.keys} yield (kq, kz)
      val stream1 = stream.map{case (k,v) => ((k,v), sendOne(k,v))}
      Map(stream0.toSeq.union(stream1.toSeq): _*)
  }

  def sendOne(queryKey: String, zoneKey: String): Seq[R00t] = {
    // https://github.com/hhru/api/blob/master/docs/clusters.md
    // https://api.hh.ru/vacancies?clusters=true&metro=6.8&area=1

    import java.net.URLEncoder
    val query: String = hhruQueries.getOrElse(queryKey, Seq()).head
    val zones: Seq[String] = hhruZones.getOrElse(zoneKey, Seq(("_","_"))).map{case (k, v) => v}

    val stream: Seq[R00t] = (0 to 9).flatMap(page => zones.flatMap(zone => {
      // https://habrahabr.ru/company/hh/blog/303168/
      // https://stackoverflow.com/questions/9593409/how-to-convert-pojo-to-json-and-vice-versa
      val param: String = "?text=" + URLEncoder.encode(query, "UTF-8") + "&area=" + "1" +
        "&search_field=name&order_by=publication_time&per_page=20&page=" + page // READ https://github.com/hhru/api/issues/278
      val extra: String = if (zone != "_") { "&metro=" + URLEncoder.encode(zone, "UTF-8") } else ""

        import sys.process._
        import scala.util.Random

        val to = "https://api.hh.ru/vacancies" + param + extra
        val k = scala.util.Random.nextInt() % 3
        Thread.sleep(500 * k * k)
        //val result: String = ("curl -k -H \"User-Agent: api-test-agent\" \"" + to + "\"").!!

      import scalaj.http.{BaseHttp, HttpConstants}
      object MyHttp extends BaseHttp (
        proxyConfig = None,
        options = HttpConstants.defaultOptions,
        charset = HttpConstants.utf8,
        userAgent = "api-test-agent"
      )
      val request = MyHttp(to)
      val result = request.asString.body


        import java.io.ByteArrayInputStream
        import java.io.InputStreamReader
        import com.google.gson.GsonBuilder

        println("DEBUG: " + to) // READ q/19277136
        println("DEBUG: " + result) // READ q/19277136
        val gson = new GsonBuilder().create()
        val is = new ByteArrayInputStream(result.getBytes("UTF-8"))
        val root = gson.fromJson(new InputStreamReader(is), classOf[R00t])
        Seq(root)
    }))
    stream.toSeq
  }

  def toCp1252(line: String): String = {
    import scala.util.{Try, Success, Failure}
    val success = Try {
      import java.io.{ByteArrayInputStream}
      import java.nio.charset.{StandardCharsets, Charset}
      val stream = new ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8.name()))
      import java.util.Scanner
      val scan = new Scanner(stream, "CP1252")
      stream.close()
      if(scan.hasNextLine) { scan.nextLine() } else ""
    }
    success match {
      case(Success(v)) => v
      case _ => ""
    }
  }

  def doOnline(): Unit = {
    val responses: Map[(String, String), Seq[R00t]] = sendRequest()
    // https://stackoverflow.com/questions/4604237/how-to-write-to-a-file-in-scala
    import com.google.gson.Gson
    import com.github.tototoshi.csv.{CSVWriter, DefaultCSVFormat}
    val g = new Gson
    responses.toSeq.foreach { case (k, v) => {
      val n = "C:\\HR\\AIVAZ\\CSV\\" + k._1 + "_" + k._2 + ".csv"
      val f = new java.io.File(n)
      implicit object MyFormat extends DefaultCSVFormat {
        override val delimiter = ';'
      }
      val p = CSVWriter.open(f)
      try {
        type MyTuple = (String, String, String, String, String, String)
        p.writeRow(List("URL", "SALARY", "W1", "W2", "W3", "HEADER"))
        val s: Seq[List[MyTuple]] = v.flatMap(j => {
          // val url = g.toJson(j)
          // val salary = g.toJson(j)
          // val w1 = "1.0"
          // val w2 = "1.0"
          // val w3 = "1.0"
          // val header = g.toJson(j)
          val items: java.util.List[Item] = j.getItems
          val szItems = if (items != null) items.size() else 0
          val res: Seq[List[MyTuple]] = (0 until szItems).map(iItems => {
            val url = items.get(iItems).getAlternateUrl
            val salary = g.toJson(items.get(iItems))
            val w1 = "1.0"
            val w2 = "1.0"
            val w3 = "1.0"
            val header = items.get(iItems).getName
            List((toCp1252(url), toCp1252(salary), toCp1252(w1), toCp1252(w2), toCp1252(w3), toCp1252(header)))
          })
          val appendix: Seq[List[MyTuple]] = (1 to 1).map(_ => {
            val url = j.getAlternateUrl
            val salary = g.toJson(j)
            val w1 = "1.0"
            val w2 = "1.0"
            val w3 = "1.0"
            val header = "ITOGO"
            List((toCp1252(url), toCp1252(salary), toCp1252(w1), toCp1252(w2), toCp1252(w3), toCp1252(header)))
          })
          res// .union(appendix)
        })
        s.foreach{(x: Seq[MyTuple]) => x.foreach(y => {
          p.writeRow(List(y._1, y._2, y._3, y._4, y._5, y._6))
        }
        )}
        s.foreach{x => println(x)}
        println("Saved " + n)
      } finally {
        p.close()
      }
     }
    }
  }
}
