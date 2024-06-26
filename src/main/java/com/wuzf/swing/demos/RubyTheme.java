package com.wuzf.swing.demos;/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * RubyTheme.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)RubyTheme.java	1.10 05/11/17
 */
 
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

// TODO: Auto-generated Javadoc
/**
 * This class describes a theme using red colors.
 *
 * @version 1.10 11/17/05
 * @author Jeff Dinkins
 */
public class RubyTheme extends DefaultMetalTheme {

    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.DefaultMetalTheme#getName()
     */
    public String getName() { return "Ruby"; }

    /** The primary1. */
    private final ColorUIResource primary1 = new ColorUIResource(80, 10, 22);
    
    /** The primary2. */
    private final ColorUIResource primary2 = new ColorUIResource(193, 10, 44);
    
    /** The primary3. */
    private final ColorUIResource primary3 = new ColorUIResource(244, 10, 66); 

    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.DefaultMetalTheme#getPrimary1()
     */
    protected ColorUIResource getPrimary1() { return primary1; }  
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.DefaultMetalTheme#getPrimary2()
     */
    protected ColorUIResource getPrimary2() { return primary2; } 
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.DefaultMetalTheme#getPrimary3()
     */
    protected ColorUIResource getPrimary3() { return primary3; } 

}
