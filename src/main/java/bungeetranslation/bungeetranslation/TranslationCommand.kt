package bungeetranslation.bungeetranslation

import bungeetranslation.bungeetranslation.TranslateUtil.translate
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Command

object TranslationCommand : Command("translate","translate") {
    override fun execute(sender: CommandSender?, args: Array<out String>?) {
        if (sender == null)return

        if (args.isNullOrEmpty())return
        val player = ProxyServer.getInstance().getPlayer(sender.name)
        val locale = player.locale.language
        player.translate(locale, args.joinToString(" "))
    }
}