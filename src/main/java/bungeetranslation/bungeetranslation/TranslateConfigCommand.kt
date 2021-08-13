package bungeetranslation.bungeetranslation

import bungeetranslation.bungeetranslation.BungeeTranslation.Companion.config
import bungeetranslation.bungeetranslation.BungeeTranslation.Companion.urllist
import bungeetranslation.bungeetranslation.TranslateUtil.loaderConfiguration
import bungeetranslation.bungeetranslation.TranslateUtil.saveConfig
import bungeetranslation.bungeetranslation.TranslateUtil.sendmsg
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Command
import java.net.MalformedURLException
import java.net.URL

object TranslateConfigCommand : Command("translateconfig","translate") {

    override fun execute(sender: CommandSender?, args: Array<out String>?) {
        if (sender == null)return
        if (args.isNullOrEmpty() && args!!.size != 1)return

        val url = args[0]

        try {
            URL(url)
        }catch (e : MalformedURLException){
            e.printStackTrace()
            return
        }

        val player = ProxyServer.getInstance().getPlayer(sender.name)
        val list = config.getStringList("urllist")
        if (urllist.containsKey(player.uniqueId)){
            list.remove(player.uniqueId.toString() + ":" + urllist[player.uniqueId])
            urllist.remove(player.uniqueId)
        }

        urllist[player.uniqueId] = url
        list.add(player.uniqueId.toString() + ":" + url)
        config.set("urllist",list)
        saveConfig()
        loaderConfiguration()

        player.sendmsg("URLを保存しました")
        return

    }
}