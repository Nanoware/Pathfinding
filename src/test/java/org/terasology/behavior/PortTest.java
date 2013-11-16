/*
 * Copyright 2013 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.behavior;

import junit.framework.Assert;
import org.junit.Test;
import org.terasology.behavior.tree.CompositeNode;
import org.terasology.behavior.tree.DecoratorNode;
import org.terasology.behavior.tree.Node;
import org.terasology.behavior.tree.Task;
import org.terasology.behavior.ui.Port;
import org.terasology.behavior.ui.PortList;
import org.terasology.behavior.ui.RenderableNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author synopia
 */
public class PortTest {
    @Test
    public void testConnectDecorator() {
        RenderableNode one = new RenderableNode();
        one.setNode(decorator(null));
        RenderableNode two = new RenderableNode();
        two.setNode(node());
        ((Port.OutputPort) one.getPortList().ports().get(0)).setTarget(two.getInputPort());

        Assert.assertEquals(Arrays.asList(two), one.getChildren());
    }

    @Test
    public void testConnectComposite() {
        RenderableNode parent = new RenderableNode();
        parent.setNode(composite());
        RenderableNode one = new RenderableNode();
        one.setNode(node());
        RenderableNode two = new RenderableNode();
        two.setNode(node());

        ((Port.OutputPort) parent.getPortList().ports().get(0)).setTarget(one.getInputPort());
        ((Port.OutputPort) parent.getPortList().ports().get(2)).setTarget(two.getInputPort());

        Assert.assertEquals(Arrays.asList(one, two), parent.getChildren());
    }

    @Test
    public void testDisconnectDecorator() {
        RenderableNode one = new RenderableNode();
        one.setNode(decorator(null));
        RenderableNode two = new RenderableNode();
        two.setNode(node());
        ((Port.OutputPort) one.getPortList().ports().get(0)).setTarget(two.getInputPort());

        Assert.assertEquals(Arrays.asList(two), one.getChildren());
        ((Port.OutputPort) one.getPortList().ports().get(0)).setTarget(null);

        List<RenderableNode> empty = new ArrayList<>();
        empty.add(null);
        Assert.assertEquals(empty, one.getChildren());

    }

    @Test
    public void testDisconnectComposite() {
        RenderableNode parent = new RenderableNode();
        parent.setNode(composite());
        RenderableNode one = new RenderableNode();
        one.setNode(node());
        RenderableNode two = new RenderableNode();
        two.setNode(node());

        ((Port.OutputPort) parent.getPortList().ports().get(0)).setTarget(one.getInputPort());
        ((Port.OutputPort) parent.getPortList().ports().get(2)).setTarget(two.getInputPort());

        ((Port.OutputPort) parent.getPortList().ports().get(1)).setTarget(null);
        Assert.assertEquals(Arrays.asList(two), parent.getChildren());

        ((Port.OutputPort) parent.getPortList().ports().get(1)).setTarget(null);
        Assert.assertEquals(Arrays.asList(), parent.getChildren());
    }

    @Test
    public void testLeaf() {
        RenderableNode node = new RenderableNode();
        node.setNode(node());
        PortList portList = node.getPortList();
        Assert.assertEquals(0, portList.ports().size());

    }

    @Test
    public void testDecorator() {
        RenderableNode node = new RenderableNode();
        node.setNode(decorator(node()));
        PortList portList = node.getPortList();
        Assert.assertEquals(1, portList.ports().size());
    }

    private Node node() {
        return new Node() {
            @Override
            public Task create() {
                return null;
            }
        };
    }

    private DecoratorNode decorator(Node node) {
        return new DecoratorNode(node) {
            @Override
            public Task create() {
                return null;
            }
        };
    }

    private CompositeNode composite() {
        return new CompositeNode() {
            @Override
            public Task create() {
                return null;
            }
        };
    }

    @Test
    public void testComposite() {
        RenderableNode node = new RenderableNode();
        node.setNode(new CompositeNode() {
            @Override
            public Task create() {
                return null;
            }
        });
        PortList portList = node.getPortList();
        Assert.assertEquals(2, portList.ports().size());
    }
}
