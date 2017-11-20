package telegram.settings

object MainConfig {
  // Telegram
  val botUserName: String = "MusicNovaBot"
  val botToken: String = "477055081:AAFexBL5sG8O0f88oBp2vDkEPms5cmMFSAU"

  val startHeader = """
  <b>Привет дружище!</b>
  Меня зовут PoetryBot, я готов сохранять твои стихи и искать похожие у классиков. Буду рад оказаться полезным :-)
  """
  val badCommand: String = "Дружище, я не понимаю что ты от меня хочешь :-)"
  val commandsHeader = "<b>Доступные команды</b>"
  val commands: List[String] = List(
    "/help и я выведу справку по доступным командам",
    "/authors и я перечислю доступных классиков",
    "/best и я сохраню твой список любимых классиков",
    "/listall и я перечислю сохраненные стихи",
    "/newone и я сохраню твой новый стих",
    "/mails и я перечислю сохраненные почтовые ящики",
    "/settings и я сохраню твои почтовые ящики"
  )

  val listsHeader = "<b>Твои записи:</b>"
  val newNotAvailable = "На данный момент шаблон недоступен. Попробуйте запросить его чуть позже."
}
