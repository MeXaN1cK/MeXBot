package mexan1ck

import mexan1ck.config.Configuration

class MeXBot {
}

fun main(){
    val config = Configuration()
    val res= config.readConfig("Config.json")
    println(config.castResultToMap(res,"discord")["apiKey"])
}