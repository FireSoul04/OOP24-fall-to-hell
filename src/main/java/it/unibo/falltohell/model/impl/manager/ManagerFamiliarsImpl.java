package it.unibo.falltohell.model.impl.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.listener.NoFamiliarsCallback;
import it.unibo.falltohell.model.api.manager.ManagerFamiliars;
import it.unibo.falltohell.model.api.gameobject.movable.FamiliarBat;
import it.unibo.falltohell.model.impl.gameobject.movable.FamiliarBatImpl;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * <p>
 * Implementation of the {@link ManagerFamiliars} interface.
 * </p>
 *
 * <p>
 * This manager handles the lifecycle and behavior of {@link FamiliarBat}
 * instances
 * summoned by a {@link Character}. Its responsibilities include:
 * </p>
 * <ul>
 * <li>Creating familiars and assigning them a life duration</li>
 * <li>Handling deferred removal if a familiar is attacking</li>
 * <li>Delegating attacks to idle familiars in range</li>
 * <li>Notifying a {@link NoFamiliarsCallback} when no familiars remain</li>
 * </ul>
 *
 * <p>
 * Each familiar has a fixed lifespan ({@link #LIFE_DURATION}) after which it is
 * automatically scheduled for removal.
 * </p>
 *
 * @see ManagerFamiliars
 * @see FamiliarBat
 * @see NoFamiliarsCallback
 * @see Character
 */
public class ManagerFamiliarsImpl implements ManagerFamiliars {
    private static final int LIFE_DURATION = 15_000;
    private final List<FamiliarBat> list = new ArrayList<>();
    private final Set<FamiliarBat> pendingRemoval = new HashSet<>();
    @SuppressFBWarnings(value = "UwF_UNWRITTEN_FIELD", justification = "Callback is initialized before usage externally")
    private NoFamiliarsCallback callback;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createFamiliar(final Character character) {
        final var familiar = new FamiliarBatImpl(character, f -> {

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
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(value = "UwF_UNWRITTEN_FIELD", justification = "Callback is initialized before usage externally")
    public void removeFamiliar(final FamiliarBat familiar) {
        if (familiar.isIdle()) {
            familiar.clearListener();
            this.list.remove(familiar);
            familiar.getLevel().getTimerManager().removeTimer(familiar.getName());
            familiar.getLevel().removeGameObject(familiar);
            if (this.list.isEmpty() && this.callback != null) {
                callback.onNoFamiliarsLeft();
            }
        } else {
            pendingRemoval.add(familiar);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attack(final Vector2 direction) {
        this.list.stream()
                .filter(f -> f.isIdle() && f.isInAttackRange())
                .findFirst()
                .ifPresent(f -> f.attack(direction));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNoFamiliarsCallback(final NoFamiliarsCallback callback) {
        this.callback = callback;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFree() {
        return this.list.stream()
                .anyMatch(f -> f.isIdle() && f.isInAttackRange());
    }
}
