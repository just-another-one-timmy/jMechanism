/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.group;

import tmm.connector.*;
import tmm.kpair.*;
import tmm.segment.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author jtimv
 */
public class GroupSTest {

    private static GroupS instance = null;
    private static Segment s0 = null, s1 = null;
    private static KPairSlide kp = null;
    private static ConnectorSlide c1 = null, c2 = null;

    public GroupSTest() {
        instance = new GroupS(kp, s0, s1);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    /*    s0 = new Segment();
        s0.setCPolus(new ConnectorTurn(s1, "cs1", new TFLinear()));
        c1 = new ConnectorSlide(s1, "c1");
        c1.setAlpha(Math.PI/3);
        c1.setPhi(Math.PI/6);
        c1.setRo(1.7);
        
        
        s1 = new Segment();
        s1.setCPolus(new ConnectorTurn(s2, "cs2", new TFLinear()));
        c2 = new ConnectorSlide(s2, "c2");
        
        
        kp = new KPairSlide("A", c1, c2);
        instance = new GroupS(kp, s0, s1);
   */
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
        GroupType expResult = GroupType.GROUP_TYPE_S;
        GroupType result = instance.getType();
        assertEquals(expResult, result);
    }
}
