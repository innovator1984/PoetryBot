package telegram.handlers

import java.lang

import telegram.settings.MainConfig.{badCommand, botUserName, commands, commandsHeader}
import telegram.settings.MainConfig.{listsHeader, newNotAvailable, startHeader}
import com.typesafe.scalalogging.LazyLogging
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message
import telegram.database.DerbyDriver

trait CommandHandler {
  def handle(msgWithCmd: Message): Seq[SendMessage]
}

class PoetryBotCommandHandler extends CommandHandler with LazyLogging {
  private def handleStart(chatId: lang.Long, userName: String, userFirstName: String, userLastName: String) = {
    val text = (startHeader :: commandsHeader :: commands).mkString("\n")
    logger.info(s"User: $userFirstName $userLastName with userName:$userName has been connected" +
      " to $botUserName (chatId:$chatId)")
    new SendMessage(chatId, text).enableHtml(true) :: Nil
  }

  private def handleAuthors(chatId: lang.Long, userName: String) = {
    val lists = new DerbyDriver().requestLists()
    val text = (listsHeader :: lists).mkString("\n")
    logger.info(s"$botUserName has sent contact information to user with userName:$userName (chatId:$chatId)")
    new SendMessage(chatId, text).enableHtml(true) :: Nil
  }

  private def handleBest(chatId: lang.Long, userName: String) = {
    val one = new DerbyDriver().requestNewOne
    if (one.isEmpty) {
      new SendMessage(chatId, newNotAvailable) :: Nil
    } else {
      logger.info(s"$botUserName has sent price list to user with userName:$userName (chatId:$chatId)")
      one.map(_.mkString("\n"))
        .map(text => new SendMessage(chatId, text))
    }
  }

  private def handleListall(chatId: lang.Long, userName: String) = {
    val lists = new DerbyDriver().requestLists()
    val text = (listsHeader :: lists).mkString("\n")
    logger.info(s"$botUserName has sent contact information to user with userName:$userName (chatId:$chatId)")
    new SendMessage(chatId, text).enableHtml(true) :: Nil
  }

  private def handleNewone(chatId: lang.Long, userName: String) = {
    val one = new DerbyDriver().requestNewOne
    if (one.isEmpty) {
      new SendMessage(chatId, newNotAvailable) :: Nil
    } else {
      logger.info(s"$botUserName has sent price list to user with userName:$userName (chatId:$chatId)")
      one.map(_.mkString("\n"))
        .map(text => new SendMessage(chatId, text))
    }
  }

  private def handleMails(chatId: lang.Long, userName: String) = {
    val lists = new DerbyDriver().requestLists()
    val text = (listsHeader :: lists).mkString("\n")
    logger.info(s"$botUserName has sent contact information to user with userName:$userName (chatId:$chatId)")
    new SendMessage(chatId, text).enableHtml(true) :: Nil
  }

  private def handleSettings(chatId: lang.Long, userName: String) = {
    val one = new DerbyDriver().requestNewOne
    if (one.isEmpty) {
      new SendMessage(chatId, newNotAvailable) :: Nil
    } else {
      logger.info(s"$botUserName has sent price list to user with userName:$userName (chatId:$chatId)")
      one.map(_.mkString("\n"))
        .map(text => new SendMessage(chatId, text))
    }
  }

  private def handleQuests(chatId: lang.Long, userName: String) = {
    val lists = new DerbyDriver().requestLists()
    val text = (listsHeader :: lists).mkString("\n")
    logger.info(s"$botUserName has sent contact information to user with userName:$userName (chatId:$chatId)")
    new SendMessage(chatId, text).enableHtml(true) :: Nil
  }

  private def handleRatings(chatId: lang.Long, userName: String) = {
    val one = new DerbyDriver().requestNewOne
    if (one.isEmpty) {
      new SendMessage(chatId, newNotAvailable) :: Nil
    } else {
      logger.info(s"$botUserName has sent price list to user with userName:$userName (chatId:$chatId)")
      one.map(_.mkString("\n"))
        .map(text => new SendMessage(chatId, text))
    }
  }

  private def handleVacancies(chatId: lang.Long, userName: String) = {
    val lists = new DerbyDriver().requestLists()
    val text = (listsHeader :: lists).mkString("\n")
    logger.info(s"$botUserName has sent contact information to user with userName:$userName (chatId:$chatId)")
    new SendMessage(chatId, text).enableHtml(true) :: Nil
  }

  private def handleTrends(chatId: lang.Long, userName: String) = {
    val one = new DerbyDriver().requestNewOne
    if (one.isEmpty) {
      new SendMessage(chatId, newNotAvailable) :: Nil
    } else {
      logger.info(s"$botUserName has sent price list to user with userName:$userName (chatId:$chatId)")
      one.map(_.mkString("\n"))
        .map(text => new SendMessage(chatId, text))
    }
  }

  override def handle(msgWithCmd: Message): Seq[SendMessage] = {
    val chatId = msgWithCmd.getChatId
    val userName = if (msgWithCmd.getChat.getUserName == null) "Empty" else msgWithCmd.getChat.getUserName
    val userFirstName = if (msgWithCmd.getChat.getFirstName == null) "Empty" else msgWithCmd.getChat.getFirstName
    val userLastName = if (msgWithCmd.getChat.getLastName == null) "Empty" else msgWithCmd.getChat.getLastName
    val msgText = msgWithCmd.getText

    msgText match {
      case "/start" =>
        handleStart(chatId, userName, userFirstName, userLastName)
      case "/authors" =>
        handleAuthors(chatId, userName)
      case "/best" =>
        handleBest(chatId, userName)
      case "/listall" =>
        handleListall(chatId, userName)
      case "/newone" =>
        handleNewone(chatId, userName)
      case "/mails" =>
        handleMails(chatId, userName)
      case "/settings" =>
        handleSettings(chatId, userName)
      case "/quests" =>
        handleQuests(chatId, userName)
      case "/ratings" =>
        handleRatings(chatId, userName)
      case "/vacancies" =>
        handleVacancies(chatId, userName)
      case "/trends" =>
        handleTrends(chatId, userName)
      case _ =>
        new SendMessage(chatId, badCommand) :: Nil
    }
  }
}
