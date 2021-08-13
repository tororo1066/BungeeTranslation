package bungeetranslation.bungeetranslation

import bungeetranslation.bungeetranslation.BungeeTranslation.Companion.config
import bungeetranslation.bungeetranslation.BungeeTranslation.Companion.plugin
import bungeetranslation.bungeetranslation.BungeeTranslation.Companion.urllist
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.net.URLEncoder


object TranslateUtil {

    fun ProxiedPlayer.sendmsg(s : String){
        this.sendMessage(*ComponentBuilder(s).create())
    }

    fun ProxiedPlayer.translate(langTo: String, text: String){

        try {
            val urlapi : String = if (urllist.containsKey(this.uniqueId)){
                urllist[this.uniqueId]!!
            }else{
                "https://script.google.com/macros/s/AKfycbyiP4X1LV7zvW_jJwglyVs5bkMb9mwXM6H4ahvbEAWy7VC3ZiF6/exec"
            }
            val strEnc = URLEncoder.encode(text,"UTF-8")
            val content = StringBuilder()

            val url = URL("$urlapi?text=$strEnc&target=$langTo")
            val urlConnection = url.openConnection()
            urlConnection.setRequestProperty("User-Agent", "mtranslate")
            val bufferedReader = BufferedReader(InputStreamReader(urlConnection.getInputStream(),"UTF-8"))
            val line = bufferedReader.readLine()
            content.append(line)
            val contentstring = content.toString().substring(20..content.toString().length-3)

            if (contentstring.contains("実行した回数が多すぎます。")){
                this.sendmsg("§c翻訳数が上限に達しています。")
                return
            }

            val comptext = ComponentBuilder("§6[翻訳] §a➜§f $contentstring").event(ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,contentstring)).currentComponent


            this.sendMessage(comptext)
            return
        }catch (e : Exception){
            e.printStackTrace()
        }
    }


    fun loaderConfiguration() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration::class.java)
                .load(File(plugin.dataFolder, "config.yml"))

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun saveConfig(){
        try {
            ConfigurationProvider.getProvider(YamlConfiguration::class.java)
                .save(config, File(plugin.dataFolder, "config.yml"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}