package com.archyx.aureliumskills.api;

import com.archyx.aureliumskills.AureliumSkills;
import com.archyx.aureliumskills.modifier.StatModifier;
import com.archyx.aureliumskills.skills.PlayerSkill;
import com.archyx.aureliumskills.skills.Skill;
import com.archyx.aureliumskills.skills.SkillLoader;
import com.archyx.aureliumskills.skills.levelers.Leveler;
import com.archyx.aureliumskills.stats.PlayerStat;
import com.archyx.aureliumskills.stats.Stat;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class AureliumAPI {

    /**
     * Gets the current mana of a player
     * @return the current mana of a player
     */
    public static int getMana(Player player) {
        return AureliumSkills.manaManager.getMana(player.getUniqueId());
    }

    /**
     * Gets the max mana of a player
     * @return the max mana of a player
     */
    public static int getMaxMana(Player player) {
        return AureliumSkills.manaManager.getMaxMana(player.getUniqueId());
    }

    /**
     * Sets a player's mana to an amount
     */
    public static void setMana(Player player, int amount) {
        AureliumSkills.manaManager.setMana(player.getUniqueId(), amount);
    }

    /**
     * Adds Skill XP to a player for a certain skill, and includes multiplier permissions
     */
    public static void addXp(Player player, Skill skill, double amount) {
        Leveler.addXp(player, skill, amount);
    }

    /**
     * Adds Skill XP to a player for a certain skill, without multipliers
     */
    public static void addXpRaw(Player player, Skill skill, double amount) {
        PlayerSkill playerSkill = SkillLoader.playerSkills.get(player.getUniqueId());
        if (playerSkill != null) {
            playerSkill.addXp(skill, amount);
            Leveler.checkLevelUp(player, skill);
            Leveler.sendActionBarMessage(player, skill, amount);
        }
    }

    /**
     * Adds Skill XP to an offline player for a certain skill
     */
    public static boolean addXpOffline(OfflinePlayer player, Skill skill, double amount) {
        PlayerSkill playerSkill = SkillLoader.playerSkills.get(player.getUniqueId());
        if (playerSkill != null) {
            playerSkill.addXp(skill, amount);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Gets the skill level of a player
     * @return the skill level of a player, or 1 if player does not have a skills profile
     */
    public static int getSkillLevel(Player player, Skill skill) {
        PlayerSkill playerSkill = SkillLoader.playerSkills.get(player.getUniqueId());
        if (playerSkill != null) {
            return playerSkill.getSkillLevel(skill);
        }
        else {
            return 1;
        }
    }

    /**
     * Gets the skill xp of a player
     * @param player The player to get from
     * @param skill The skill to get
     * @return The current skill xp
     */
    public static double getXp(Player player, Skill skill) {
        PlayerSkill playerSkill = SkillLoader.playerSkills.get(player.getUniqueId());
        if (playerSkill != null) {
            return playerSkill.getXp(skill);
        }
        else {
            return 1;
        }
    }

    /**
     * Gets the stat level of a player
     * @param player The player to get from
     * @param stat The stat to get
     * @return The stat level
     */
    public static int getStatLevel(Player player, Stat stat) {
        PlayerStat playerStat = SkillLoader.playerStats.get(player.getUniqueId());
        if (playerStat != null) {
            return playerStat.getStatLevel(stat);
        }
        else {
            return 0;
        }
    }

    /**
     * Gets the base stat level of a player, without modifiers
     * @param player The player to get from
     * @param stat The stat to get
     * @return The stat level without modifiers
     */
    public static int getBaseStatLevel(Player player, Stat stat) {
        PlayerStat playerStat = SkillLoader.playerStats.get(player.getUniqueId());
        if (playerStat != null) {
            return playerStat.getBaseStatLevel(stat);
        }
        else {
            return 0;
        }
    }

    /**
     * Adds a stat modifier to a player
     * @param player The player to add to
     * @param name The name of the stat modifier
     * @param stat The stat to add to
     * @param value The value of the modifier
     * @return true if a modifier was added, false if the player does not have a skills profile
     */
    public static boolean addStatModifier(Player player, String name, Stat stat, int value) {
        PlayerStat playerStat = SkillLoader.playerStats.get(player.getUniqueId());
        if (playerStat != null) {
            playerStat.addModifier(new StatModifier(name, stat, value));
            return true;
        }
        return false;
    }

    /**
     * Removes a stat modifier from a player
     * @param player The player to remove from
     * @param name The name of the stat modifier
     * @return true if the operation was successful, false if the stat modifier was not found or the player does not have a skills profile
     */
    public static boolean removeStatModifier(Player player, String name) {
        PlayerStat playerStat = SkillLoader.playerStats.get(player.getUniqueId());
        if (playerStat != null) {
            return playerStat.removeModifier(name);
        }
        return false;
    }


}
