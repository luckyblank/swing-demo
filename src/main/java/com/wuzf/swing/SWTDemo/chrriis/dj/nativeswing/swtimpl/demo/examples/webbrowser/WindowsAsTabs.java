/*
 * Christopher Deckers (chrriis@nextencia.net)
 * http://www.nextencia.net
 *
 * See the file "readme.txt" for information on usage and redistribution of
 * this file, and for a DISCLAIMER OF ALL WARRANTIES.
 */
package com.wuzf.swing.SWTDemo.chrriis.dj.nativeswing.swtimpl.demo.examples.webbrowser;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import chrriis.dj.nativeswing.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.*;
import com.wuzf.swing.utils.TabPanel;

/**
 * @author Christopher Deckers
 */
public class WindowsAsTabs {

  protected static final String LS = System.getProperty("line.separator");

  public static JComponent createContent() {
    JPanel contentPane = new JPanel(new BorderLayout());
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    JWebBrowser webBrowser = new JWebBrowser();
    webBrowser.setBarsVisible(false);
    webBrowser.setStatusBarVisible(true);
    webBrowser.setHTMLContent(
        "<html>" + LS +
        "  <body>" + LS +
        "    <a href=\"https://www.baidu.com\">https://www.baidu.com</a>: normal link.<br/>" + LS +
        "    <a href=\"https://www.luckyblank.cn\" target=\"_blank\">https://www.luckyblank.com</a>: link that normally opens in new window.<br/>" + LS +
        "  </body>" + LS +
        "</html>");
    addWebBrowserListener(tabbedPane, webBrowser);
    tabbedPane.addTab("起始页", webBrowser);
    contentPane.add(tabbedPane, BorderLayout.CENTER);
    return contentPane;
  }

  private static void addWebBrowserListener(final JTabbedPane tabbedPane, final JWebBrowser webBrowser) {
    webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
      @Override
      public void titleChanged(WebBrowserEvent e) {
        System.out.println("titleChanged....");
        for(int i=0; i<tabbedPane.getTabCount(); i++) {
          if(tabbedPane.getComponentAt(i) == webBrowser) {
            if(i == 0) {
              return;
            }
            tabbedPane.setTitleAt(i, webBrowser.getPageTitle());
            break;
          }
        }
      }
      @Override
      public void windowWillOpen(WebBrowserWindowWillOpenEvent e) {
        System.out.println("windowWillOpen....");
        JWebBrowser newWebBrowser = new JWebBrowser();
        addWebBrowserListener(tabbedPane, newWebBrowser);
        tabbedPane.addTab("", newWebBrowser);
        tabbedPane.setTabComponentAt(1,new TabPanel("Tab"+tabbedPane.getSelectedIndex() +1, tabbedPane));

        System.out.println("当前选中"+tabbedPane.getSelectedIndex());
        e.setNewWebBrowser(newWebBrowser);
        tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex() +1);
        System.out.println("当前选中"+tabbedPane.getSelectedIndex());
      }
      @Override
      public void windowOpening(WebBrowserWindowOpeningEvent e) {
        System.out.println("windowOpening....");
        e.getWebBrowser().setMenuBarVisible(false);
      }


      public void windowClosing(WebBrowserEvent e) {
        System.out.println("windowClosing....");
      }

      public void locationChanging(WebBrowserNavigationEvent e) {
        System.out.println("locationChanging....");
      }

      public void locationChanged(WebBrowserNavigationEvent e) {
        System.out.println("locationChanged....");
      }

      public void locationChangeCanceled(WebBrowserNavigationEvent e) {
        System.out.println("locationChangeCanceled....");
      }

      public void loadingProgressChanged(WebBrowserEvent e) {
        System.out.println("loadingProgressChanged....");
      }

      public void statusChanged(WebBrowserEvent e) {
        System.out.println("statusChanged....");
      }

      public void commandReceived(WebBrowserCommandEvent e) {
        System.out.println("commandReceived....");
      }

    });
  }

  /* Standard main method to try that test as a standalone application. */
  public static void main(String[] args) {
    NativeInterface.open();
    UIUtils.setPreferredLookAndFeel();
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        JFrame frame = new JFrame("DJ Native Swing Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(createContent(), BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
      }
    });
    NativeInterface.runEventPump();
  }

}
