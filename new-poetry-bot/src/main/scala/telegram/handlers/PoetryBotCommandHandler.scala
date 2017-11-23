package telegram.handlers

import telegram.settings.MainConfig.{badCommand, botUserName, commands, commandsHeader}
import telegram.settings.MainConfig.{listsHeader, startHeader, newNotAvailable}
import com.typesafe.scalalogging.LazyLogging
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message
import telegram.database.DerbyDriver

trait CommandHandler {
  def handle(msgWithCmd: Message): Seq[SendMessage]
}

class PoetryBotCommandHandler extends CommandHandler with LazyLogging {
  override def handle(msgWithCmd: Message): Seq[SendMessage] = {
    val chatId = msgWithCmd.getChatId
    val userName = if (msgWithCmd.getChat.getUserName == null) "Empty" else msgWithCmd.getChat.getUserName
    val userFirstName = if (msgWithCmd.getChat.getFirstName == null) "Empty" else msgWithCmd.getChat.getFirstName
    val userLastName = if (msgWithCmd.getChat.getLastName == null) "Empty" else msgWithCmd.getChat.getLastName
    val msgText = msgWithCmd.getText

    msgText match {
      case "/start" =>
        val text = (startHeader :: commandsHeader :: commands).mkString("\n")
        logger.info(s"User: $userFirstName $userLastName with userName:$userName has been connected" +
          " to $botUserName (chatId:$chatId)")
        new SendMessage(chatId, text).enableHtml(true) :: Nil
/*FIXME*/      case "/authors" =>
/*FIXME*/        val lists = new DerbyDriver().requestLists()
/*FIXME*/        val text = (listsHeader :: lists).mkString("\n")
/*FIXME*/        logger.info(s"$botUserName has sent contact information to user with userName:$userName (chatId:$chatId)")
/*FIXME*/        new SendMessage(chatId, text).enableHtml(true) :: Nil
/*FIXME*/      case "/best" =>
/*FIXME*/        val one = new DerbyDriver().requestNewOne
/*FIXME*/        if (one.isEmpty) new SendMessage(chatId, newNotAvailable) :: Nil
/*FIXME*/        else {
/*FIXME*/          logger.info(s"$botUserName has sent price list to user with userName:$userName (chatId:$chatId)")
/*FIXME*/          one.map(_.mkString("\n"))
/*FIXME*/            .map(text => new SendMessage(chatId, text))
/*FIXME*/        }
      case "/listall" =>
        val lists = new DerbyDriver().requestLists()
        val text = (listsHeader :: lists).mkString("\n")
        logger.info(s"$botUserName has sent contact information to user with userName:$userName (chatId:$chatId)")
        new SendMessage(chatId, text).enableHtml(true) :: Nil
      case "/newone" =>
        val one = new DerbyDriver().requestNewOne
        if (one.isEmpty) new SendMessage(chatId, newNotAvailable) :: Nil
        else {
          logger.info(s"$botUserName has sent price list to user with userName:$userName (chatId:$chatId)")
          one.map(_.mkString("\n"))
            .map(text => new SendMessage(chatId, text))
        }
/*FIXME*/      case "/mails" =>
/*FIXME*/        val lists = new DerbyDriver().requestLists()
/*FIXME*/        val text = (listsHeader :: lists).mkString("\n")
/*FIXME*/        logger.info(s"$botUserName has sent contact information to user with userName:$userName (chatId:$chatId)")
/*FIXME*/        new SendMessage(chatId, text).enableHtml(true) :: Nil
/*FIXME*/      case "/settings" =>
/*FIXME*/        val one = new DerbyDriver().requestNewOne
/*FIXME*/        if (one.isEmpty) new SendMessage(chatId, newNotAvailable) :: Nil
/*FIXME*/        else {
/*FIXME*/          logger.info(s"$botUserName has sent price list to user with userName:$userName (chatId:$chatId)")
/*FIXME*/          one.map(_.mkString("\n"))
/*FIXME*/            .map(text => new SendMessage(chatId, text))
/*FIXME*/        }
      case _ =>
        new SendMessage(chatId, badCommand) :: Nil
    }
  }
}
