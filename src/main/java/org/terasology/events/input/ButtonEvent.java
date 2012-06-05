/*
 * Copyright 2012
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.terasology.events.input;

import org.terasology.entitySystem.EntityRef;
import org.terasology.game.client.ButtonState;

/**
 * @author Immortius
 */
public abstract class ButtonEvent extends InputEvent {

    protected ButtonState state;

    public ButtonEvent(float delta, EntityRef target) {
        super(delta, target);
    }

    public ButtonState getState() {
        return state;
    }

    public abstract String getButtonName();
}
