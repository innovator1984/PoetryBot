package telegram

import com.google.gson.GsonBuilder
import telegram.vacancieshhru.R00t

import sys.process._

object HhRu {
  def sendRequest(): R00t = {
    // https://habrahabr.ru/company/hh/blog/303168/
    // https://stackoverflow.com/questions/9593409/how-to-convert-pojo-to-json-and-vice-versa
    val param: String = "?text=java&area=1&metro=6.8"
    val result: String = ("curl -k -H \"User-Agent: api-test-agent\" \"https://api.hh.ru/vacancies" + param + "\"").!!
    println("DEBUG: " + result)
    val gson = new GsonBuilder().create()
    val root = gson.fromJson(result, classOf[R00t])
    root
  }

  def getAreaCode(name: String): String = {
    // https://github.com/hhru/api/blob/master/docs/areas.md
    name
  }

  def getMetroCode(name: String): String = {
    // https://github.com/hhru/api/blob/master/docs/metro.md
    name
  }
}
