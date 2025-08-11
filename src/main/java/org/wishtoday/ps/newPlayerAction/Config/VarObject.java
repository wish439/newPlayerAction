package org.wishtoday.ps.newPlayerAction.Config;

import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VarObject implements ConfigurationSerializable {
    private String varName;
    private String varText;
    private ClickEvent clickEvent;
    private String hoverText;

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        VarObject varObject = (VarObject) object;
        return Objects.equals(varName, varObject.varName) && Objects.equals(varText, varObject.varText) && Objects.equals(clickEvent, varObject.clickEvent) && Objects.equals(hoverText, varObject.hoverText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(varName, varText, clickEvent, hoverText);
    }

    @Override
    public String toString() {
        return "VarObject{" +
                "varName='" + varName + '\'' +
                ", varText='" + varText + '\'' +
                ", clickEvent=" + clickEvent +
                ", hoverText='" + hoverText + '\'' +
                '}';
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public String getVarText() {
        return varText;
    }

    public void setVarText(String varText) {
        this.varText = varText;
    }

    public ClickEvent getClickEvent() {
        return clickEvent;
    }

    public void setClickEvent(ClickEvent clickEvent) {
        this.clickEvent = clickEvent;
    }

    public String getHoverText() {
        return hoverText;
    }

    public void setHoverText(String hoverText) {
        this.hoverText = hoverText;
    }

    private VarObject(String varName, String varText, ClickEvent clickEvent, String hoverText) {
        this.varName = varName;
        this.varText = varText;
        this.clickEvent = clickEvent;
        this.hoverText = hoverText;
    }
    public static VarObject of(String varName
            , String varText
            , ClickEvent clickEvent, String hoverText) {
        return new VarObject(varName, varText, clickEvent, hoverText);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("varName", varName);
        map.put("varText", varText);
        map.put("clickEvent", serializeClickEvent(clickEvent));
        map.put("hoverText", hoverText);
        return map;
    }

    public static VarObject deserialize(Map<String, Object> map) {
        return new VarObject(
                (String) map.get("varName")
                , (String) map.get("varText")
                , deserializeClickEvent((Map<String, String>) map.get("clickEvent"))
                , (String) map.get("hoverText"));
    }

    private static Map<String, String> serializeClickEvent(ClickEvent clickEvent) {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", clickEvent.action().name());
        map.put("value", clickEvent.value());
        return map;
    }

    private static ClickEvent deserializeClickEvent(Map<String, String> map) {
        return ClickEvent.clickEvent(ClickEvent.Action.valueOf(map.get("action")), map.get("value"));
    }
}
