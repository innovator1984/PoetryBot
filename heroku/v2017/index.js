require('./bot');
require('./web');
// your token from BotFather
var token = '468875771:AAFGwrUaTqk6T7Q2AbNgQDRMoyPsc6ZzA4Q'
var Bot = require('node-telegram-bot-api'),
    bot = new Bot(token, { polling: true });
bot.onText(/^\/echo$/, (msg, match) => {
    bot.sendMessage(msg.chat.id, 'You said ' + match[1])
});
