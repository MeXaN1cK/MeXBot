package mexan1ck

import com.google.gson.JsonObject
import mexan1ck.clients.DiscordPeer
import mexan1ck.clients.IrcPeer
import mexan1ck.clients.Peer
import mexan1ck.log.Log

class Bridge(internal val config: JsonObject) {
    private val discordConn: Peer
    private val ircConn: Peer
    init {
        discordConn = DiscordPeer(this)
        ircConn = IrcPeer(this)
    }
    fun startBridge() {
        try {
            discordConn.start()
            ircConn.start()
        } catch (ex: Exception) { // just catch everything - "conditions that a reasonable application might want to catch"
            Log.error("Unable to initialize bridge connections: $ex")
            ex.printStackTrace()
            this.shutdown()
            return
        }
        Log.info("Bridge initialized and running")
    }
    internal fun shutdown() {
        Log.debug("Stopping bridge...")

        //discordConn.shutdown()
        //ircConn.shutdown()

        Log.info("Bridge stopped")
    }
}