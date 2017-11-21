package telegram

import com.google.gson.GsonBuilder
import telegram.vacancieshhru.R00t

import sys.process._

object HhRu {
  def sendRequest(): R00t = {
    // https://habrahabr.ru/company/hh/blog/303168/
    // https://stackoverflow.com/questions/9593409/how-to-convert-pojo-to-json-and-vice-versa
    val param: String = "?text=scala&area=" + getAreaCode("MOSCOW") // + "&metro=" + getMetroCode("RIMSKAYA")
    val result: String = ("curl -k -H \"User-Agent: api-test-agent\" \"https://api.hh.ru/vacancies" + param + "\"").!!

    // val result = "" // TODO
    
    println("DEBUG: " + result)
    val gson = new GsonBuilder().create()
    val root = gson.fromJson(result, classOf[R00t])
    root
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
