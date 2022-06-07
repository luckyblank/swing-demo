/*
 * Copyright (C), 2022-2022, Freedom Person
 * FileName: JwebBrowser.java
 * Author:   lucky
 * Date:     2022/1/16 16:11
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名    修改时间    版本号       描述
 */
package com.wuzf.swing.utils;


import chrriis.dj.nativeswing.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.*;
import com.wuzf.swing.demos.SwingSet2;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.util.Hashtable;
import java.util.Map;

/**
 * 功能描述:<br>
 *
 * @author lucky
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */

public class WzfJwebFrame extends JInternalFrame {

    public static JWebBrowser webBrowser ;	//浏览器模型

    public static Map<String,JWebBrowser> t_jwbrowsers=new Hashtable<String, JWebBrowser>();

//    private static final String URL = "https://www.luckyblank.cn";
   // private static final String URL = "file:///D:/project/recent/jdk-test/Demo/summer/index.html";

    public static  JInternalFrame createInternalFrame(String url) throws PropertyVetoException {
        JInternalFrame internalFrame = new JInternalFrame(
                "WzfJwebBrowser",  // title
                true,       // resizable
                true,       // closable
                true,       // maximizable
                true        // iconifiable
        );

        internalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        internalFrame.setSize(1183, 700);
        // 设置窗口的显示位置
        internalFrame.setLocation(0, 0);
        internalFrame.setLayout(null);

        webBrowser  = new JWebBrowser();
        webBrowser .navigate(url);
        webBrowser .setPreferredSize(new Dimension(800,400));
        webBrowser .setBarsVisible(false);
        webBrowser .setMenuBarVisible(false);
        webBrowser .setButtonBarVisible(false);
        webBrowser .setStatusBarVisible(false);

        //添加事件监听
        webBrowser.addWebBrowserListener(new WebBrowserListener() {

            @Override
            public void windowWillOpen(WebBrowserWindowWillOpenEvent arg0) {
                JWebBrowser jwb=arg0.getNewWebBrowser();
                String location=jwb.getResourceLocation();
                System.out.println("windowWillOpen: "+location);
            }

            @Override
            public void windowOpening(WebBrowserWindowOpeningEvent arg0) {
                  JWebBrowser jwb=arg0.getNewWebBrowser();
                  String location=jwb.getResourceLocation();
                  System.out.println(location);
            }

            @Override
            public void windowClosing(WebBrowserEvent arg0) {
                System.out.println("windowClosing....");

            }

            @Override
            public void titleChanged(WebBrowserEvent arg0) {

            }

            @Override
            public void statusChanged(WebBrowserEvent arg0) {

            }

            @Override
            public void locationChanging(WebBrowserNavigationEvent arg0) {
                String location=arg0.getNewResourceLocation();
                System.out.println("locationChanging: "+location);
            }

            @Override
            public void locationChanged(WebBrowserNavigationEvent arg0) {
                String location=arg0.getNewResourceLocation();
                System.out.println("locationChanged: "+location);

            }

            @Override
            public void locationChangeCanceled(WebBrowserNavigationEvent arg0) {

            }

            @Override
            public void loadingProgressChanged(WebBrowserEvent arg0) {

            }

            @Override
            public void commandReceived(WebBrowserCommandEvent arg0) {

            }
        });

        //存储URL和webrowser关系
        t_jwbrowsers.put(url, webBrowser);

        // 创建内容面板
        JPanel panel = new JPanel(new BorderLayout());

        // 添加组件到面板
        panel.add(webBrowser,BorderLayout.CENTER);

        // 设置内部窗口的内容面板
        internalFrame.setContentPane(panel);
        // 显示内部窗口
        internalFrame.setVisible(true);

        return  internalFrame;
    }


    public static void main(String[] args) {
        //ui
        SwingSet2.initBeautyStyle();

        //初始化Native Swing
        UIUtils.setPreferredLookAndFeel();

        if(!NativeInterface.isOpen()){

            NativeInterface.initialize();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {

                    creatGUI();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //介绍网站https://sourceforge.net/p/djproject/discussion/671154/thread/e813001e/
        NativeInterface.runEventPump();


    }
    public static JWebBrowser getJWebBrowser(String url){
        return t_jwbrowsers.get(url);
    }

    public static JFrame creatGUI() throws PropertyVetoException {

        JFrame jf = new JFrame("测试窗口");
        jf.setSize(1200, 800);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //创建桌面面板
        JDesktopPane desktopPane = new JDesktopPane();  //桌面面板

        // 创建 内部窗口
        String url = "https://www.luckyblank.cn";
         url = "https://www.baidu.com";
        JInternalFrame internalFrame = createInternalFrame(url);

        // 添加 内部窗口 到 桌面面板
        desktopPane.add(internalFrame);

        desktopPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);  //设置内部窗体拖动模式
//        jf.getContentPane().add(desktopPane,BorderLayout.CENTER);; // 设置 contentPane 属性。
        jf.setContentPane(desktopPane);
        jf.setVisible(true);
        return jf;
    }

}
