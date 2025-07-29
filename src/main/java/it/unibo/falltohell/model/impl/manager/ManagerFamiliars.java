package it.unibo.falltohell.model.impl.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.listener.NoFamiliarsCallback;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.model.impl.gameobject.movable.FamiliarBat;
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
    private static final int LIFE_DURATION = 15_000;
    private final List<FamiliarBat> list = new ArrayList<>();
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
        final var familiar = new FamiliarBat(character, f -> {

            if (pendingRemoval.contains(f)) {
                pendingRemoval.remove(f);
                removeFamiliar(f);
            }
        });
        list.add(familiar);
        final String name = "Active-" + UUID.randomUUID();
        familiar.getLevel().getTimerManager().addTimer(name, new CustomTimerImpl(LIFE_DURATION, () -> {
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
            if (this.list.isEmpty()) {
                callback.onNoFamiliarsLeft();
            }
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
    public void setNoFamiliarsCallback(final NoFamiliarsCallback callback) {
        this.callback = callback;
    }

    /**
     * Checks whether there is at least one familiar that is both idle and within
     * attack range.
     *
     * @return {@code true} if a familiar is ready to attack; {@code false}
     *         otherwise
     */
    public boolean isFree() {
        return this.list.stream()
                .anyMatch(f -> f.isIdle() && f.isInAttackRange());
    }
}
