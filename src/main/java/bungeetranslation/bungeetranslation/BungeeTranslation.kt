package bungeetranslation.bungeetranslation

import bungeetranslation.bungeetranslation.TranslateUtil.loaderConfiguration
import bungeetranslation.bungeetranslation.TranslateUtil.saveConfig
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.config.Configuration
import java.io.File
import java.io.IOException
import java.util.*


class BungeeTranslation : Plugin() {

    override fun onEnable() {
        plugin = this

        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }
        val configFile = File(dataFolder, "config.yml")
        if (!configFile.exists()){
            try {
                configFile.createNewFile()
            }catch (e : IOException){
                e.printStackTrace()
            }
        }
        loaderConfiguration()

        for (list in config.getStringList("urllist")){
            val uuid = list.split(":")[0]
            val url = list.split(":")[1]

            urllist[UUID.fromString(uuid)] = url
        }
        proxy.pluginManager.registerCommand(this,TranslationCommand)
        proxy.pluginManager.registerCommand(this,TranslateConfigCommand)
    }

    companion object{
        lateinit var plugin : BungeeTranslation
        val urllist = HashMap<UUID,String>()
        lateinit var config : Configuration
    }

}