package trmenu.api.action.impl

import trmenu.api.action.base.AbstractAction
import trmenu.api.action.base.ActionOption
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit
import trmenu.api.action.base.ActionDesc

/**
 * @author Arasple
 * @date 2021/2/8 22:25
 */
class ActionDelArguments : AbstractAction() {

    override fun onExecute(player: Player, placeholderPlayer: Player) {
        submit(delay = 3L, async = true) {
            player.session().arguments = arrayOf()
        }
    }

    companion object : ActionDesc {

        override val name = "(clear|cls|del|rem)-?arg(ument)?s?".toRegex()

        override val parser: (Any, ActionOption) -> AbstractAction = { _, _ ->
            ActionDelArguments()
        }

    }

}