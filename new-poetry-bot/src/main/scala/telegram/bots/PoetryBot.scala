package telegram.bots

import org.telegram.telegrambots.api.methods.BotApiMethod
import org.telegram.telegrambots.api.objects.{Message, Update}
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import telegram.handlers.PoetryBotCommandHandler
import telegram.settings.MainConfig.{botToken, botUserName}

class PoetryBot extends TelegramLongPollingBot {
  override def getBotToken = botToken

  override def getBotUsername = botUserName

  private val cmdHandler = new PoetryBotCommandHandler
  override def onUpdateReceived(update: Update) = {
    cmdHandler.handle(update.getMessage).foreach(execute[Message, BotApiMethod[Message]])
  }
}
