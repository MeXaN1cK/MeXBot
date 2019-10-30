package mexan1ck.clients

import mexan1ck.message.Destination
import mexan1ck.message.Message

interface Peer {
    fun start()
    fun shutddown()
    fun sendMessage(target: Destination, message: Message)
}