/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ochan.whatwhatwiki.control;

import junit.framework.TestCase;

/**
 *
 * @author denki
 */
public class PreviewControllerTest extends TestCase {
    
    public PreviewControllerTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of processLinks method, of class PreviewController.
     */
    public void testProcessLinks() {
        PreviewController pc = new PreviewController();
        System.out.println(pc.processLinks("here is a [[link]]"));
        
        System.out.println(pc.processLinks("here is a [[link]]\n[[LINK]]\n[[Multi part space link]][[no space]]"));
    }

}
