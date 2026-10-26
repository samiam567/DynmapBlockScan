package org.dynmapblockscan.core.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Vector3DTest {
    @Test
    void constructorAndSet() {
        Vector3D v = new Vector3D(1, 2, 3);
        assertEquals(1, v.x);
        assertEquals(2, v.y);
        assertEquals(3, v.z);
        Vector3D copy = new Vector3D(v);
        assertEquals(v, copy);
        Vector3D z = new Vector3D();
        z.set(v);
        assertEquals(v, z);
    }

    @Test
    void operations() {
        Vector3D a = new Vector3D(1, 2, 3);
        a.add(new Vector3D(4, 5, 6));
        assertEquals(new Vector3D(5, 7, 9), a);
        a.subtract(new Vector3D(1, 1, 1));
        assertEquals(new Vector3D(4, 6, 8), a);
        a.scale(2);
        assertEquals(new Vector3D(8, 12, 16), a);
        a.scale(new Vector3D(1, 2, 3));
        assertEquals(new Vector3D(8, 24, 48), a);
        assertEquals(8*1 + 24*2 + 48*3, a.innerProduct(new Vector3D(1, 2, 3)), 1e-9);
    }

    @Test
    void crossProduct() {
        Vector3D a = new Vector3D(1, 0, 0);
        a.crossProduct(new Vector3D(0, 1, 0));
        assertEquals(new Vector3D(0, 0, 1), a);
    }

    @Test
    void equalityHashCode() {
        Vector3D a = new Vector3D(1, 2, 3);
        Vector3D b = new Vector3D(1, 2, 3);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, new Vector3D(1, 2, 4));
        assertNotEquals(a, "x");
    }
}
