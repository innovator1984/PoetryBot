package telegram.database

class DummyDriver {
  def requestLists(): List[String] = List("Босс: +7 (000) 000-00-00")
  val requestNewOne: List[String] = List("Бла", "Бла", "Бла!")
}
