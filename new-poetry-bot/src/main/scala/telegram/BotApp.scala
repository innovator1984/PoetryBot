package telegram

import com.typesafe.scalalogging.LazyLogging
import org.telegram.telegrambots.exceptions.TelegramApiRequestException
import org.telegram.telegrambots.{ApiContextInitializer, TelegramBotsApi}
import telegram.bots.PoetryBot
import telegram.settings.MainConfig.{botUserName}

object BotApp extends App with LazyLogging {
  // READ https://github.com/rubenlagus/TelegramBots/wiki/Getting-Started

  ApiContextInitializer.init()
  val botsApi = new TelegramBotsApi

  try botsApi.registerBot(new PoetryBot)
  catch {
    case ex: TelegramApiRequestException =>
      logger.error(s"Registration of $botUserName has been failed", ex)
      System.exit(1)
  }
  logger.info(s"Registration of $botUserName has been success")
}
