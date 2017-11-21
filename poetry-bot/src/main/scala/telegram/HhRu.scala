package telegram

import java.io.ByteArrayInputStream

import com.google.gson.GsonBuilder
import telegram.vacancieshhru.R00t

// import sys.process._

object HhRu {
  def sendRequest(): Seq[R00t] = {
    // https://habrahabr.ru/company/hh/blog/303168/
    // https://stackoverflow.com/questions/9593409/how-to-convert-pojo-to-json-and-vice-versa
    val param: String = "?text=scala&area=" + getAreaCode("MOSCOW") // + "&metro=" + getMetroCode("RIMSKAYA")
    // val result: String = ("curl -k -H \"User-Agent: api-test-agent\" \"https://api.hh.ru/vacancies" + param + "\"").!!
    import scalaj.http.{BaseHttp, HttpConstants}
    object MyHttp extends BaseHttp (
      proxyConfig = None,
      options = HttpConstants.defaultOptions,
      charset = HttpConstants.utf8,
      userAgent = "api-test-agent"
    )

    val request = MyHttp("https://api.hh.ru/vacancies" + param)
    val result = request.asString.body

    import java.io.ByteArrayInputStream
    import java.io.InputStreamReader

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
