package trmenu.api.action.impl

import trmenu.api.action.base.AbstractAction
import trmenu.api.action.base.ActionOption
import org.bukkit.entity.Player
import trmenu.api.action.base.ActionDesc
import kotlin.math.min

/**
 * @author Arasple
 * @date 2021/2/1 23:22
 */
class ActionPage(content: String, option: ActionOption) : AbstractAction(content, option) {

    override fun onExecute(player: Player, placeholderPlayer: Player) {
        val session = player.session()
        val menu = session.menu ?: return
        val page = min(parseContent(placeholderPlayer).toIntOrNull() ?: 0, menu.layout.getSize() - 1)

        menu.page(player, page)
    }

    companion object : ActionDesc {

        override val name = "(set|switch)?-?(layout|shape|page)s?".toRegex()

        override val parser: (Any, ActionOption) -> AbstractAction = { value, option ->
            ActionPage(value.toString(), option)
        }

    }

}