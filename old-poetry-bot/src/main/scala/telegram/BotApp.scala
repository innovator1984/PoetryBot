package telegram

import com.typesafe.scalalogging.LazyLogging
import org.telegram.telegrambots.exceptions.TelegramApiRequestException
import org.telegram.telegrambots.{ApiContextInitializer, TelegramBotsApi}
import telegram.bots.PoetryBot
import telegram.exporthhru.VacanciesExport
import telegram.handlers.PoetryBotCommandHandler
import telegram.settings.MainConfig.botUserName

object BotApp extends App with LazyLogging {

  val hhru = HhRu
  val responses = hhru.sendRequest()
  logger.info(s"HhRu RESPONSES: " + responses.length)

  val export = VacanciesExport
  export.calcNQueensCombinations(8)
  export.update(responses)

  val handler = new PoetryBotCommandHandler()
  handler.handleStart(0, "BotName", "BotFirstName", "BotLastName")
  handler.handleAuthors(0, "BotName")
  handler.handleBest(0, "BotName")
  handler.handleListall(0, "BotName")
  handler.handleNewone(0, "BotName")
  handler.handleMails(0, "BotName")
  handler.handleSettings(0, "BotName")
  handler.handleQuests(0, "BotName")
  handler.handleRatings(0, "BotName")
  handler.handleVacancies(0, "BotName")
  handler.handleTrends(0, "BotName")

  // READ https://github.com/rubenlagus/TelegramBots/wiki/Getting-Started
  /*
    ApiContextInitializer.init()
    val botsApi = new TelegramBotsApi

    try botsApi.registerBot(new PoetryBot)
    catch {
      case ex: TelegramApiRequestException =>
        logger.error(s"Registration of $botUserName has been failed", ex)
        System.exit(1)
    }
    logger.info(s"Registration of $botUserName has been success")
  */
}
