package com.archyx.aureliumskills.skills.levelers;

import com.archyx.aureliumskills.AureliumSkills;
import com.archyx.aureliumskills.configuration.Option;
import com.archyx.aureliumskills.configuration.OptionL;
import com.archyx.aureliumskills.skills.Skill;
import com.archyx.aureliumskills.skills.Source;
import com.archyx.aureliumskills.skills.abilities.FightingAbilities;
import com.archyx.aureliumskills.util.VersionUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class FightingLeveler implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDeath(EntityDeathEvent event) {
		if (OptionL.isEnabled(Skill.FIGHTING)) {
			LivingEntity e = event.getEntity();
			//Checks if in blocked world
			if (AureliumSkills.worldManager.isInBlockedWorld(e.getLocation())) {
				return;
			}
			//Checks if in blocked region
			if (AureliumSkills.worldGuardEnabled) {
				if (AureliumSkills.worldGuardSupport.isInBlockedRegion(e.getLocation())) {
					return;
				}
			}
			if (e.getKiller() != null) {
				if (e.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
					EntityDamageByEntityEvent ee = (EntityDamageByEntityEvent) e.getLastDamageCause();
					if (!(ee.getDamager() instanceof Arrow)) {
						EntityType type = e.getType();
						Player p = e.getKiller();
						Skill s = Skill.FIGHTING;
						//Check for permission
						if (!p.hasPermission("aureliumskills.fighting")) {
							return;
						}
						//Check creative mode disable
						if (OptionL.getBoolean(Option.DISABLE_IN_CREATIVE_MODE)) {
							if (p.getGameMode().equals(GameMode.CREATIVE)) {
								return;
							}
						}
						if (type.equals(EntityType.CHICKEN) || type.equals(EntityType.BAT) || type.equals(EntityType.OCELOT) || type.equals(EntityType.RABBIT)) {
							Leveler.addXp(p, s, FightingAbilities.getModifiedXp(p, Source.FIGHTING_SMALL_PASSIVE));
						}
						else if (type.equals(EntityType.COW) || type.equals(EntityType.PIG) || type.equals(EntityType.SHEEP) || type.equals(EntityType.MUSHROOM_COW) ||
								type.equals(EntityType.SQUID) || type.equals(EntityType.HORSE) || type.equals(EntityType.SNOWMAN) || type.equals(EntityType.MULE) ||
								type.equals(EntityType.DONKEY) || type.equals(EntityType.PARROT)) {
							Leveler.addXp(p, s, FightingAbilities.getModifiedXp(p, Source.FIGHTING_PASSIVE));
						}
						else if (type.equals(EntityType.LLAMA) || type.equals(EntityType.WOLF) || type.equals(EntityType.SILVERFISH) || type.equals(EntityType.ENDERMITE)) {
							Leveler.addXp(p, s, FightingAbilities.getModifiedXp(p, Source.FIGHTING_WEAK_HOSTILE));
						}
						else if (type.equals(EntityType.ZOMBIE) || type.equals(EntityType.SKELETON) || type.equals(EntityType.SPIDER) || type.equals(EntityType.ZOMBIE_VILLAGER)
								|| type.name().equalsIgnoreCase("DROWNED") || VersionUtils.isPigman(type)) {
							Leveler.addXp(p, s, FightingAbilities.getModifiedXp(p, Source.FIGHTING_COMMON_HOSTILE));
						}
						else if (type.equals(EntityType.CREEPER) || type.equals(EntityType.STRAY) || type.equals(EntityType.HUSK) || type.equals(EntityType.CAVE_SPIDER) ||
								type.equals(EntityType.SLIME) || type.equals(EntityType.MAGMA_CUBE) || type.equals(EntityType.VEX) || type.equals(EntityType.GUARDIAN)) {
							Leveler.addXp(p, s, FightingAbilities.getModifiedXp(p, Source.FIGHTING_UNCOMMON_HOSTILE));
						}
						else if (type.equals(EntityType.GHAST) || type.equals(EntityType.BLAZE) || type.equals(EntityType.ENDERMAN) || type.equals(EntityType.WITCH)) {
							Leveler.addXp(p, s, FightingAbilities.getModifiedXp(p, Source.FIGHTING_STRONG_HOSTILE));
						}
						else if (type.equals(EntityType.WITHER_SKELETON) || type.equals(EntityType.VINDICATOR) || type.equals(EntityType.POLAR_BEAR) || 
								type.equals(EntityType.SHULKER) || type.equals(EntityType.IRON_GOLEM)) {
							Leveler.addXp(p, s, FightingAbilities.getModifiedXp(p, Source.FIGHTING_STRONGER_HOSTILE));
						}
						else if (type.equals(EntityType.ELDER_GUARDIAN) || type.equals(EntityType.EVOKER)) {
							Leveler.addXp(p, s, FightingAbilities.getModifiedXp(p, Source.FIGHTING_MINI_BOSS));
						}
						else if (type.equals(EntityType.WITHER) || type.equals(EntityType.ENDER_DRAGON)) {
							Leveler.addXp(p, s, FightingAbilities.getModifiedXp(p, Source.FIGHTING_BOSS));
						}
						else if (type.equals(EntityType.PLAYER)) {
							Leveler.addXp(p, s, FightingAbilities.getModifiedXp(p, Source.FIGHTING_PLAYER));
						}
					}
				}
			}
		}
	}
	
}
