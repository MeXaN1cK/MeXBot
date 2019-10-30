package mexan1ck.clients

import mexan1ck.Bridge
import mexan1ck.log.Log
import mexan1ck.message.Destination
import mexan1ck.message.Message
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.entities.TextChannel
import net.dv8tion.jda.webhook.WebhookClient
import net.dv8tion.jda.webhook.WebhookClientBuilder
import net.dv8tion.jda.webhook.WebhookMessageBuilder

private const val ZERO_WIDTH_SPACE = 0x200B.toChar()

class DiscordPeer(private val bridge: Bridge) : Peer {
    private lateinit var discordApi: JDA
    private val webhookMap = HashMap<String, WebhookClient>()
    override fun start() {
        Log.info("Connecting to Discord API...")
        val discordApiBuilder = JDABuilder(AccountType.BOT)
            .setToken(bridge.config["apiKey"].toString())
            .addEventListener(DiscordListener(this))
        discordApi = discordApiBuilder
            .build()
            .awaitReady()
            .awaitStatus(JDA.Status.INITIALIZED)
        Log.info("Initializing Discord webhooks")
        lateinit var webhook: WebhookClient
        try {
            webhook = WebhookClientBuilder(bridge.config["channelWebHookURL"].toString()).build()
        } catch (ex: IllegalArgumentException) {
            Log.error("Webhook for ${bridge.config["channelName"]} with url ${bridge.config["channelWebHookURL"]} is not valid!")
            ex.printStackTrace()
        }
        Log.info("Webhook for ${bridge.config["channelName"]} registered")
        webhookMap[bridge.config["channelName"].toString()] = webhook
        Log.info("Connected to Discord!")
    }

    override fun shutddown() {
        discordApi.shutdownNow()
        for (client in webhookMap.values) {
            client.close()
        }
    }

    override fun sendMessage(target: Destination, message: Message) {
        if (!this::discordApi.isInitialized) {
            Log.error("Discord Connection has not been initialized yet!")
            return
        }
        val channel = getTextChannelBy(bridge.config["channelName"].toString())
        if (channel == null) {
            Log.error("Unable to get a discord channel for: ${bridge.config["channelName"]} | Is bot present?")
            return
        }
        val webhook = webhookMap[bridge.config["channelName"].toString()]
        if (webhook != null) {
            //sendMessageWebhook(webhook, message.sender, message.content)
        } else {
            //sendMessageOldStyle(channel, message.sender, message.content)
        }
    }
//    private fun sendMessageOldStyle(discordChannel: TextChannel,nick: String ,msg: String) {
//        //val msg = checkMessage(nick,msg)
//        if (!discordChannel.canTalk()) {
//            Log.warn("Bridge cannot speak in ${discordChannel.name} to send message: $msg")
//            return
//        }
//        val senderName = nick
//        discordChannel.sendMessage("**$senderName**: $msg").queue()
//    }
//     private fun sendMessageWebhook(webhook: WebhookClient, nick: String ,msg: String) {
//        //val msg = checkMessage(nick,msg)
//        val senderName = nick
//        val message = WebhookMessageBuilder()
//            .setContent("**$senderName**: $msg")
//            .setUsername(senderName)
//            .build()
//        webhook.send(message)
//    }

    private fun getTextChannelBy(string: String): TextChannel? {
        val byId = discordApi.getTextChannelById(string)
        if (byId != null) {
            return byId
        }
        val byName = discordApi.getTextChannelsByName(string, false) ?: return null
        return if (byName.isNotEmpty()) byName.first() else null
    }
}