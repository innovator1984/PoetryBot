package telegram.handlers

import telegram.settings.MainConfig.{badCommand, botUserName, commands, commandsHeader}
import telegram.settings.MainConfig.{listsHeader, newNotAvailable, startHeader}
import com.typesafe.scalalogging.LazyLogging
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message
import telegram.database.{AccountsExport, DummyDriver}

trait CommandHandler {
  def handle(msgWithCmd: Message): Seq[SendMessage]
}

class PoetryBotCommandHandler extends CommandHandler with LazyLogging {
  def handleStart(chatId: Long, userName: String, userFirstName: String, userLastName: String) = {
    val accounts = AccountsExport
    accounts.update(Seq(userName))
    val acc = accounts.search(Seq(userName))

    val text = (startHeader :: commandsHeader :: commands).mkString("\n")
    logger.info(s"User: $userFirstName $userLastName with userName:$userName has been connected" +
      " to $botUserName (chatId:$chatId)")
    new SendMessage(chatId, "CARD" + acc + " " + text).enableHtml(true) :: Nil
  }

  def handleAuthors(chatId: Long, userName: String) = {
    val accounts = AccountsExport
    val acc = accounts.search(Seq(userName))

    val lists = new DummyDriver().requestLists()
    val text = (listsHeader :: lists).mkString("\n")
    logger.info(s"$botUserName has sent contact information to user with userName:$userName (chatId:$chatId)")
    new SendMessage(chatId, "CARD" + acc + " " + text).enableHtml(true) :: Nil
  }

  def handleBest(chatId: Long, userName: String) = {
    val accounts = AccountsExport
    val acc = accounts.search(Seq(userName))

    val one = new DummyDriver().requestNewOne
    if (one.isEmpty) {
      new SendMessage(chatId, newNotAvailable) :: Nil
    } else {
      logger.info(s"$botUserName has sent price list to user with userName:$userName (chatId:$chatId)")
      one.map(_.mkString("\n"))
        .map(text => new SendMessage(chatId, "CARD" + acc + " " + text))
    }
  }

  def handleListall(chatId: Long, userName: String) = {
    val accounts = AccountsExport
    val acc = accounts.search(Seq(userName))

    val lists = new DummyDriver().requestLists()
    val text = (listsHeader :: lists).mkString("\n")
    logger.info(s"$botUserName has sent contact information to user with userName:$userName (chatId:$chatId)")
    new SendMessage(chatId, "CARD" + acc + " " + text).enableHtml(true) :: Nil
  }

  def handleNewone(chatId: Long, userName: String) = {
    val accounts = AccountsExport
    val acc = accounts.search(Seq(userName))

    val one = new DummyDriver().requestNewOne
    if (one.isEmpty) {
      new SendMessage(chatId, newNotAvailable) :: Nil
    } else {
      logger.info(s"$botUserName has sent price list to user with userName:$userName (chatId:$chatId)")
      one.map(_.mkString("\n"))
        .map(text => new SendMessage(chatId, "CARD" + acc + " " + text))
    }
  }

  def handleMails(chatId: Long, userName: String) = {
    val accounts = AccountsExport
    val acc = accounts.search(Seq(userName))

    val lists = new DummyDriver().requestLists()
    val text = (listsHeader :: lists).mkString("\n")
    logger.info(s"$botUserName has sent contact information to user with userName:$userName (chatId:$chatId)")
    new SendMessage(chatId, "CARD" + acc + " " + text).enableHtml(true) :: Nil
  }

  def handleSettings(chatId: Long, userName: String) = {
    val accounts = AccountsExport
    val acc = accounts.search(Seq(userName))

    val one = new DummyDriver().requestNewOne
    if (one.isEmpty) {
      new SendMessage(chatId, newNotAvailable) :: Nil
    } else {
      logger.info(s"$botUserName has sent price list to user with userName:$userName (chatId:$chatId)")
      one.map(_.mkString("\n"))
        .map(text => new SendMessage(chatId, "CARD" + acc + " " + text))
    }
  }

  def handleQuests(chatId: Long, userName: String) = {
    val accounts = AccountsExport
    val acc = accounts.search(Seq(userName))

    val lists = new DummyDriver().requestLists()
    val text = (listsHeader :: lists).mkString("\n")
    logger.info(s"$botUserName has sent contact information to user with userName:$userName (chatId:$chatId)")
    new SendMessage(chatId, "CARD" + acc + " " + text).enableHtml(true) :: Nil
  }

  def handleRatings(chatId: Long, userName: String) = {
    val accounts = AccountsExport
    val acc = accounts.search(Seq(userName))

    val one = new DummyDriver().requestNewOne
    if (one.isEmpty) {
      new SendMessage(chatId, newNotAvailable) :: Nil
    } else {
      logger.info(s"$botUserName has sent price list to user with userName:$userName (chatId:$chatId)")
      one.map(_.mkString("\n"))
        .map(text => new SendMessage(chatId, "CARD" + acc + " " + text))
    }
  }

  def handleVacancies(chatId: Long, userName: String) = {
    val accounts = AccountsExport
    val acc = accounts.search(Seq(userName))

    val lists = new DummyDriver().requestLists()
    val text = (listsHeader :: lists).mkString("\n")
    logger.info(s"$botUserName has sent contact information to user with userName:$userName (chatId:$chatId)")
    new SendMessage(chatId, "CARD" + acc + " " + text).enableHtml(true) :: Nil
  }

  def handleTrends(chatId: Long, userName: String) = {
    val accounts = AccountsExport
    val acc = accounts.search(Seq(userName))

    val one = new DummyDriver().requestNewOne
    if (one.isEmpty) {
      new SendMessage(chatId, newNotAvailable) :: Nil
    } else {
      logger.info(s"$botUserName has sent price list to user with userName:$userName (chatId:$chatId)")
      one.map(_.mkString("\n"))
        .map(text => new SendMessage(chatId, "CARD" + acc + " " + text))
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
