/*
 * Copyright 2017 MovingBlocks
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
package org.terasology.logic.behavior;

import org.terasology.logic.behavior.asset.BehaviorTree;
import org.terasology.logic.behavior.core.Actor;
import org.terasology.logic.behavior.core.BehaviorNode;
import org.terasology.logic.behavior.core.BehaviorState;
import org.terasology.logic.behavior.core.BehaviorTreeRunner;
import org.terasology.logic.behavior.core.DelegateNode;

/**
 * Tree runner, that runs the given tree.
 */
public class DefaultBehaviorTreeRunner implements BehaviorTreeRunner {
    private final BehaviorTree tree;
    private final BehaviorNode root;
    private Callback callback;
    private Actor actor;
    private BehaviorState state = BehaviorState.UNDEFINED;

    public DefaultBehaviorTreeRunner(BehaviorNode node, Actor actor) {
        this.tree = null;
        this.root = node.deepCopy();
        this.actor = actor;
    }

    public DefaultBehaviorTreeRunner(BehaviorTree tree, Actor actor, Callback callback) {
        this.callback = callback;
        this.tree = tree;
        this.root = injectDelegates(tree.getRoot().deepCopy(), tree.getRoot());

        this.actor = actor;
    }

    private BehaviorNode injectDelegates(BehaviorNode newNode, BehaviorNode treeNode) {
        if (newNode.getChildrenCount() == 0) {
            return createCallbackNode(newNode, treeNode);
        } else {
            for (int i = 0; i < newNode.getChildrenCount(); i++) {
                newNode.replaceChild(i, injectDelegates(newNode.getChild(i), treeNode.getChild(i)));
            }
            return createCallbackNode(newNode, treeNode);
        }
    }

    private DelegateNode createCallbackNode(BehaviorNode newNode, final BehaviorNode treeNode) {
        return new DelegateNode(newNode) {
            @Override
            public BehaviorState execute(Actor theActor) {
                BehaviorState result = super.execute(theActor);
                if (callback != null) {
                    callback.afterExecute(treeNode, result);
                }
                return result;
            }
        };
    }

    @Override
    public BehaviorTree getTree() {
        return tree;
    }

    @Override
    public BehaviorState step() {
        if (state != BehaviorState.RUNNING) {
            root.construct(actor);
        }
        
        state = root.execute(actor);
        if (state != BehaviorState.RUNNING) {
            root.destruct(actor);
        }

        return state;
    }

    @Override
    public Actor getActor() {
        return actor;
    }

    @Override
    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public interface Callback {
        void afterExecute(BehaviorNode node, BehaviorState state);
    }
}
