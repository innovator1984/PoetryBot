import com.google.gson.GsonBuilder
import telegram.vacancieshhru.R00t

object HhRu {
  def sendRequests(): Map[(String, String), Seq[R00t]] = {
    val hhruQueries: Map[String, Seq[String]] = Map("S" -> Seq("scala"),
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

    val stream0 = for {kq <- hhruQueries.keys} yield ((kq, "_"), sendOne(kq, "_"))
    val stream = for {kq <- hhruQueries.keys
                      kz <- hhruZones.keys} yield ((kq, kz), sendOne(kq, kz))
    Map(stream0.toSeq.union(stream.toSeq): _*)
  }

  def sendOne(query: String, zone: String): Seq[R00t] = {
    // https://habrahabr.ru/company/hh/blog/303168/
    // https://stackoverflow.com/questions/9593409/how-to-convert-pojo-to-json-and-vice-versa
    val param: String = "?text="+query+"&area=" + getAreaCode("MOSCOW") // + "&metro=" + getMetroCode("RIMSKAYA")
    val extra: String = if (zone != "_") "&metro=" + zone else ""
    // val result: String = ("curl -k -H \"User-Agent: api-test-agent\" \"https://api.hh.ru/vacancies" + param + "\"").!!
    import scalaj.http.{BaseHttp, HttpConstants}
    object MyHttp extends BaseHttp (
      proxyConfig = None,
      options = HttpConstants.defaultOptions,
      charset = HttpConstants.utf8,
      userAgent = "api-test-agent"
    )

    val request = MyHttp("https://api.hh.ru/vacancies" + param + extra)
    val result = request.asString.body

    import java.io.{ByteArrayInputStream, InputStreamReader}

    println("DEBUG: " + result) // READ q/19277136
    val gson = new GsonBuilder().create()
    val is = new ByteArrayInputStream( result.getBytes( "UTF-8" ) )
    val root = gson.fromJson(new InputStreamReader(is), classOf[R00t])
    Seq(root)
  }

  def getAreaCode(name: String): String = {
    // https://github.com/hhru/api/blob/master/docs/areas.md
    "1"
  }

  def getMetroCode(name: String): String = {
    // https://github.com/hhru/api/blob/master/docs/metro.md
    "10.113"
  }
}
