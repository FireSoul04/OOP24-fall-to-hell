package it.unibo.falltohell.model.impl.gameobjects.movable.entity.character;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.weapons.NoFamiliarsCallback;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons.FamiliarBat;
import it.unibo.falltohell.util.Vector2;

/**
 * Manages the familiars (e.g., bats) summoned by a character such as a druid.
 * <p>
 * Handles familiar creation, attack delegation, and scheduled removal after a
 * fixed duration or after attacks.
 * Ensures proper cleanup by deferring removal if the familiar is still
 * attacking.
 * </p>
 *
 * @author Sara Visani
 * @see FamiliarBat
 * @see Character
 */
public class ManagerFamiliars {
    private List<FamiliarBat> list = new ArrayList<>();
    private final Set<FamiliarBat> pendingRemoval = new HashSet<>();
    private NoFamiliarsCallback callback;

    /**
     * Creates a new familiar linked to the specified character.
     * <p>
     * The familiar is given a timer of 5 seconds after which removal is attempted.
     * If it is currently attacking, the removal is deferred until the attack ends.
     * </p>
     *
     * @param character the character summoning the familiar
     */
    public void createFamiliar(final Character character) {
        var familiar = new FamiliarBat(character, f -> {

            if (pendingRemoval.contains(f)) {
                pendingRemoval.remove(f);
                removeFamiliar(f);
            }
        });
        list.add(familiar);
        final String name = "Active-" + UUID.randomUUID();
        familiar.getLevel().getTimerManager().addTimer(name, new CustomTimerImpl(5000, () -> {
            this.removeFamiliar(familiar);
        }));
    }

    /**
     * Attempts to remove the familiar from the game.
     * <p>
     * If the familiar is currently idle, it is removed immediately.
     * Otherwise, the removal is deferred and handled after the current attack ends.
     * Notifies callback if no familiars remain.
     * </p>
     *
     * @param familiar the familiar to remove
     */
    public void removeFamiliar(final FamiliarBat familiar) {
        if (familiar.isIdle()) {
            familiar.clearListener();
            this.list.remove(familiar);
            familiar.getLevel().getTimerManager().removeTimer(familiar.getName());
            familiar.getLevel().removeGameObject(familiar);
            if (this.list.isEmpty())
                callback.onNoFamiliarsLeft();
        } else {
            pendingRemoval.add(familiar);
        }
    }

    /**
     * Delegates an attack command to the first available familiar in range.
     * <p>
     * The attack is only executed by an idle familiar that is close enough to the
     * character.
     * </p>
     *
     * @param direction the direction in which the familiar should attack
     */
    public void attack(final Vector2 direction) {
        this.list.stream()
                .filter(f -> f.isIdle() && f.isInAttackRange())
                .findFirst()
                .ifPresent(f -> f.attack(direction));
    }

    /**
     * Sets the callback to be notified when no familiars remain.
     *
     * @param callback the callback instance
     */
    public void setNoFamiliarsCallback(NoFamiliarsCallback callback) {
        this.callback = callback;
    }
}
