// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.engine.logic.actions;

import com.google.common.collect.Lists;
import org.terasology.engine.audio.StaticSound;
import org.terasology.engine.entitySystem.Component;

import java.util.Arrays;
import java.util.List;

/**
 * When activated, plays a random sound
 */
public class PlaySoundActionComponent implements Component {
    public List<StaticSound> sounds = Lists.newArrayList();
    public float volume = 1.0f;
    public ActionTarget relativeTo = ActionTarget.Instigator;

    public PlaySoundActionComponent() {
    }

    /**
     * Creates new instance of PlaySoundActionComponent
     *
     * @param sounds provided sounds via vararg
     */
    public PlaySoundActionComponent(StaticSound... sounds) {
        this.sounds.addAll(Arrays.asList(sounds));
    }
}