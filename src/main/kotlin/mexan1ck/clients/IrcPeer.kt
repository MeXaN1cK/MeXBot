package mexan1ck.clients

import mexan1ck.Bridge
import mexan1ck.message.Destination
import mexan1ck.message.Message

class IrcPeer(private val bridge: Bridge) : Peer {
    override fun start() {
    }

    override fun shutddown() {
    }

    override fun sendMessage(target: Destination, message: Message) {
    }
}