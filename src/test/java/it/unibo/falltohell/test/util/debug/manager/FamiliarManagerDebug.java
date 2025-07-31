package it.unibo.falltohell.test.util.debug.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.listener.NoFamiliarsCallback;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.test.util.debug.druid.FamiliarBatDebug;
import it.unibo.falltohell.util.Vector2;

/**
 * Debug manager for handling {@link FamiliarBatDebug} instances.
 * <p>
 * This class manages the lifecycle of familiars, including creation,
 * timed expiration, manual removal, pending removal for busy familiars,
 * and attack logic. It can also notify a callback when no familiars
 * remain active.
 * </p>
 *
 * @author Sara Visani
 */
public class FamiliarManagerDebug {

    private static final int LIFE_DURATION = 3000;
    private final List<FamiliarBatDebug> list = new ArrayList<>();
    private final Set<FamiliarBatDebug> pendingRemoval = new HashSet<>();
    @SuppressFBWarnings(value = "UwF_UNWRITTEN_FIELD", justification = "Callback is initialized before usage externally")
    private NoFamiliarsCallback callback;

    /**
     * Creates a new familiar for the given character.
     * <p>
     * The familiar will automatically be removed after its life duration expires.
     * If it is busy when removal is requested, it will be added to a pending
     * removal set and removed once it becomes idle.
     * </p>
     *
     * @param character the character owning the familiar
     * @return the created {@link FamiliarBatDebug} instance
     */
    public FamiliarBatDebug createFamiliar(final Character character) {
        final var familiar = new FamiliarBatDebug(character, f -> {

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

        return familiar;
    }

    /**
     * Removes a familiar from management.
     * <p>
     * If the familiar is idle, it is immediately removed and its timer is cleared.
     * If it is busy, it is added to the pending removal set and will be removed
     * once idle. When the last familiar is removed, the
     * {@link #callback} is invoked (if set).
     * </p>
     *
     * @param familiar the familiar to remove
     */
    @SuppressFBWarnings(value = "UwF_UNWRITTEN_FIELD", justification = "Callback is initialized before usage externally")
    public void removeFamiliar(final FamiliarBatDebug familiar) {
        if (familiar.isIdle()) {
            familiar.clearListener();
            this.list.remove(familiar);
            if (familiar.getLevel().getTimerManager().searchTimer(familiar.getName())) {
                familiar.getLevel().getTimerManager().removeTimer(familiar.getName());
            }
            familiar.getLevel().removeGameObject(familiar);
            if (this.list.isEmpty() && this.callback != null) {
                callback.onNoFamiliarsLeft();
            }
        } else {
            pendingRemoval.add(familiar);
        }
    }

    /**
     * Commands the first idle familiar in attack range to attack in the given
     * direction.
     *
     * @param direction the attack direction as a {@link Vector2}
     */
    public void attack(final Vector2 direction) {
        this.list.stream()
                .filter(f -> f.isIdle() && f.isInAttackRange())
                .findFirst()
                .ifPresent(f -> f.attack(direction));
    }

    /**
     * Sets the callback to be invoked when no familiars remain.
     *
     * @param callback the {@link NoFamiliarsCallback} instance
     */
    public void setNoFamiliarsCallback(final NoFamiliarsCallback callback) {
        this.callback = callback;
    }

    /**
     * Checks if any idle familiar is currently available to attack.
     *
     * @return {@code true} if an idle familiar is in attack range; {@code false}
     *         otherwise
     */
    public boolean isFree() {
        return this.list.stream()
                .anyMatch(f -> f.isIdle() && f.isInAttackRange());
    }

    /**
     * Returns the life duration of each familiar in milliseconds.
     *
     * @return the life duration in milliseconds
     */
    public static int getLifeDuration() {
        return LIFE_DURATION;
    }

    /**
     * Returns the list of active familiars managed by this class.
     *
     * @return the list of active {@link FamiliarBatDebug} instances
     */
    public List<FamiliarBatDebug> getList() {
        return List.copyOf(list);
    }

    /**
     * Returns the set of familiars pending removal.
     * <p>
     * These familiars will be removed automatically once they become idle.
     * </p>
     *
     * @return a set of familiars waiting to be removed
     */
    public Set<FamiliarBatDebug> getPendingRemoval() {
        return Set.copyOf(pendingRemoval);
    }

    /**
     * Returns the callback invoked when all familiars are gone.
     *
     * @return the {@link NoFamiliarsCallback} instance, or {@code null} if not set
     */
    public NoFamiliarsCallback getCallback() {
        return callback;
    }
}
