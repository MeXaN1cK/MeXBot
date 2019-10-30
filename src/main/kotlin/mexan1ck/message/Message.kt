package mexan1ck.message

data class Message(val sender: String, var content: String, var destination: Destination){
    fun shouldSendTo(destinationType: PlatformType): Boolean {
        return when (this.destination) {
            Destination.IRC -> destinationType == PlatformType.IRC
            Destination.DISCORD -> destinationType == PlatformType.DISCORD
        }
    }
    fun checkMessage(message: Message){

    }
}