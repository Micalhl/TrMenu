package trmenu.api.action.impl

import trmenu.api.TrMenuAPI
import trmenu.api.action.base.AbstractAction
import trmenu.api.action.base.ActionOption
import trmenu.api.event.MenuOpenEvent
import trmenu.module.internal.data.Metadata
import org.bukkit.entity.Player
import trmenu.api.action.base.ActionDesc

/**
 * @author Arasple
 * @date 2021/1/31 16:33
 *
 * open: <MenuId>:<PageIndex> <Arguments>
 */
class ActionOpen(content: String, option: ActionOption) : AbstractAction(content, option) {

    override fun onExecute(player: Player, placeholderPlayer: Player) {
        val args = parseContentSplited(placeholderPlayer, " ")
        val split = args[0].split(":")
        val arguments = if (args.size == 1) arrayOf() else args.toTypedArray().copyOfRange(1, args.size)
        val menu = TrMenuAPI.getMenuById(split[0]) ?: return
        val page = split.getOrNull(1)?.toIntOrNull() ?: 0

        menu.open(player, page, MenuOpenEvent.Reason.PLAYER_COMMAND) {
            if (!Metadata.byBukkit(player, "FORCE_ARGS") || arguments.isNotEmpty()) {
                if (arguments.isEmpty()) {
                    it.arguments = it.implicitArguments.clone()
                    it.implicitArguments = arrayOf()
                } else {
                    it.arguments = arguments
                }
            }
        }
    }

    companion object : ActionDesc {

        override val name = "opens?|(open)?-?gui|(tr)?menu".toRegex()

        override val parser: (Any, ActionOption) -> AbstractAction = { value, option ->
            ActionOpen(value.toString(), option)
        }

    }

}