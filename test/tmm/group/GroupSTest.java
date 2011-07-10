/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.group;

import tmm.connector.*;
import tmm.kpair.*;
import tmm.segment.*;
import org.junit.*;
import tmm.tf.TFLinear;
import static org.junit.Assert.*;

/**
 *
 * @author jtimv
 */
public class GroupSTest {

    private static GroupS instance = null;
    private static Segment s1 = null, s2 = null;
    private static KPairSlide kp = null;
    private static ConnectorSlide c1 = null, c2 = null;

    public GroupSTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        s1 = new Segment();
        s2 = new Segment();
        c1 = new ConnectorSlide(s1, "c1");
        c2 = new ConnectorSlide(s2, "c2");
        s1.setCPolus(new ConnectorTurn(s1, "cs1", new TFLinear()));
        s2.setCPolus(new ConnectorTurn(s2, "cs2", new TFLinear()));
        kp = new KPairSlide("A", c1, c2);
        instance = new GroupS(kp, s1, s1);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of calcTF0 method, of class GroupS.
     */
    @Test
    public void testCalcTF0() throws Exception {
        System.out.println("calcTF0");
        instance.calcTF0();
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of calcTF1 method, of class GroupS.
     */
    @Test
    public void testCalcTF1() {
        System.out.println("calcTF1");
        instance.calcTF1();
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of calcTF2 method, of class GroupS.
     */
    @Test
    public void testCalcTF2() {
        System.out.println("calcTF2");
        instance.calcTF2();
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of calcReaction method, of class GroupS.
     */
    @Test
    public void testCalcReaction() {
        System.out.println("calcReaction");
        instance.calcReaction();
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getType method, of class GroupS.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        GroupType expResult = GroupType.GROUP_TYPE_S;
        GroupType result = instance.getType();
        assertEquals(expResult, result);
    }
}
