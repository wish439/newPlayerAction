package org.wishtoday.ps.newPlayerAction.Util;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class ConfigAction {
    private Actions action;
    private List<String> value;

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        ConfigAction that = (ConfigAction) object;
        return action == that.action && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, value);
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    public ConfigAction(Actions action, List<String> value) {
        this.action = action;
        this.value = value;
    }

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
    }

    public void runAction(Player player) {
        value.forEach(value -> this.action.run(player, value));
    }

    @Override
    public String toString() {
        return "ConfigAction{" +
                "action=" + action +
                ", value=" + value +
                '}';
    }
}
