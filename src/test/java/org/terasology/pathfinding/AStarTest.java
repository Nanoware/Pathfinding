/*
 * Copyright 2014 MovingBlocks
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
package org.terasology.pathfinding;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.terasology.navgraph.BitMap;
import org.terasology.pathfinding.model.AStar;

import java.util.Collections;
import java.util.List;

/**
 * @author synopia
 */
public class AStarTest {
    @Test
    public void maze() {
        assertAStar(
                "XXXXXXXX",
                "XS**** X",
                "XXXXXX*X",
                "X **** X",
                "X*XXXXXX",
                "X **** X",
                "XXXXXX*X",
                "XE**** X",
                "XXXXXXXX"
        );

    }

    @Test
    public void simple() {
        assertAStar(
                "XXXXX",
                "XS*EX",
                "XXXXX"
        );
        assertAStar(
                "XXXXXXXX",
                "XS     X",
                "X *    X",
                "X  *   X",
                "X   *  X",
                "X    * X",
                "X     EX",
                "XXXXXXXX"
        );
        assertAStar(
                "XXXXXXXX",
                "X     EX",
                "X    * X",
                "X   *  X",
                "X  *   X",
                "X *    X",
                "XS     X",
                "XXXXXXXX"
        );
        assertAStar(
                "XXXXXXXX",
                "XE     X",
                "X *    X",
                "X  *   X",
                "X   *  X",
                "X    * X",
                "X     SX",
                "XXXXXXXX"
        );
        assertAStar(
                "XXXXXXXX",
                "X     SX",
                "X    * X",
                "X   *  X",
                "X  *   X",
                "X *    X",
                "XE     X",
                "XXXXXXXX"
        );
    }

    private void assertAStar(String... data) {
        BitMap map = new BitMap();
        int start = -1;
        int end = -1;
        List<Integer> expected = Lists.newArrayList();
        for (int y = 0; y < data.length; y++) {
            String row = data[y];
            for (int x = 0; x < row.length(); x++) {
                int offset = map.offset(x, y);
                switch (row.charAt(x)) {
                    case 'X':
                        break;
                    case 'S':
                        start = offset;
                        map.setPassable(start);
                        expected.add(offset);
                        break;
                    case 'E':
                        end = offset;
                        map.setPassable(end);
                        expected.add(offset);
                        break;
                    case '*':
                        map.setPassable(offset);
                        expected.add(offset);
                        break;
                    case ' ':
                        map.setPassable(offset);
                        break;
                    default:
                        break;
                }
            }
        }

        AStar sut = new AStar(map);
        sut.run(start, end);
        List<Integer> path = sut.getPath();
        Collections.sort(path);
        Collections.sort(expected);
        Assert.assertArrayEquals(expected.toArray(), path.toArray());
    }
}
