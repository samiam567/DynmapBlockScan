package org.dynmapblockscan.core.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Matrix3DTest {
    @Test
    void identityTransform() {
        Matrix3D m = new Matrix3D();
        Vector3D v = new Vector3D(1, 2, 3);
        m.transform(v);
        assertEquals(new Vector3D(1, 2, 3), v);
        double[] arr = {1, 2, 3};
        m.transform(arr);
        assertArrayEquals(new double[]{1, 2, 3}, arr, 1e-9);
    }

    @Test
    void scale() {
        Matrix3D m = new Matrix3D();
        m.scale(2, 3, 4);
        Vector3D v = new Vector3D(1, 1, 1);
        m.transform(v);
        assertEquals(new Vector3D(2, 3, 4), v);
    }

    @Test
    void rotateXZ90() {
        Matrix3D m = new Matrix3D();
        m.rotateXZ(90);
        Vector3D v = new Vector3D(1, 0, 0);
        m.transform(v);
        assertEquals(0, v.x, 1e-9);
        assertEquals(0, v.y, 1e-9);
        assertEquals(1, v.z, 1e-9);
    }

    @Test
    void transformToOutput() {
        Matrix3D m = new Matrix3D();
        Vector3D in = new Vector3D(2, 3, 4);
        Vector3D out = new Vector3D();
        m.transform(in, out);
        assertEquals(in, out);
    }

    @Test
    void multiplyComposition() {
        Matrix3D a = new Matrix3D();
        a.scale(2, 2, 2);
        Matrix3D b = new Matrix3D();
        b.scale(3, 3, 3);
        a.multiply(b);
        Vector3D v = new Vector3D(1, 1, 1);
        a.transform(v);
        assertEquals(new Vector3D(6, 6, 6), v);
    }
}
