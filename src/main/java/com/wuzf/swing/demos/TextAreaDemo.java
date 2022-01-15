package com.wuzf.swing.demos;

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TextAreaDemo extends DemoModule {

    /**
     * main method allows us to run as a standalone demo.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
    //初始化样式
	SwingSet2.initBeautyStyle();

	TextAreaDemo demo = new TextAreaDemo(null);
	demo.mainImpl();
    }

    /* (non-Javadoc)
     * @see DemoModule#getName()
     */
    @Override public String getName() {
    	return "文本域";
    };

    /**
     * ProgressBarDemo Constructor.
     *
     * @param swingset the swingset
     */
    public TextAreaDemo(SwingSet2 swingset) {
	// Set the title for this demo, and an icon used to represent this
	// demo inside the SwingSet2 app.
	super(swingset, "TextAreaDemo"
			, "toolbar/JProgressBar.gif");

		createProgressPanel();
    }


    /** The load action. */
    Action loadAction;
    
    /** The stop action. */
    Action stopAction;
    
    /** The progress bar. */
    JProgressBar progressBar;
    
    /** The progress text area. */
    JTextArea progressTextArea;

    /* (non-Javadoc)
     * @see DemoModule#updateDragEnabled(boolean)
     */
	@Override
	void updateDragEnabled(boolean dragEnabled) {
        progressTextArea.setDragEnabled(dragEnabled);
    }
    
    /**
     * Creates the progress panel.
     */
    public void createProgressPanel() {
	getDemoPanel().setLayout(new BorderLayout());

	JPanel textWrapper = new JPanel(new BorderLayout());
//	textWrapper.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
	textWrapper.setAlignmentX(LEFT_ALIGNMENT);
	progressTextArea = new MyTextArea();
        
	progressTextArea.getAccessibleContext().setAccessibleName(getString("ProgressBarDemo.accessible_text_area_name"));
	progressTextArea.getAccessibleContext().setAccessibleName(getString("ProgressBarDemo.accessible_text_area_description"));


	textWrapper.add(new JScrollPane(progressTextArea), BorderLayout.CENTER);


	getDemoPanel().add(textWrapper, BorderLayout.CENTER);

	JPanel progressPanel = new JPanel();
	getDemoPanel().add(progressPanel, BorderLayout.SOUTH);


		JPanel p1 = new JPanel();
		p1.add(createLoadButton());
		p1.add(createStopButton());
		
		progressPanel.add(p1);

    }

    /**
     * Creates the load button.
     *
     * @return the j button
     */
    public JButton createLoadButton() {
	loadAction = new AbstractAction(getString("ProgressBarDemo.start_button")) {
	    @Override
		public void actionPerformed(ActionEvent e) {
			progressTextArea.append("开始"+getString("ProgressBarDemo.text"));

	    }
	};
	return createButton(loadAction);
    }

    /**
     * Creates the stop button.
     *
     * @return the j button
     */
    public JButton createStopButton() {
	stopAction = new AbstractAction("开始解析") {
	    @Override
		public void actionPerformed(ActionEvent e) {
			AccessibleContext accessibleContext = progressTextArea.getAccessibleContext();
			String tempStr = progressTextArea.getText();


			progressTextArea.setText(tempStr);	;
			JOptionPane.showMessageDialog(
					getDemoPanel(),
					getString("OptionPaneDemo.warningtext"),
					getString("OptionPaneDemo.warningtitle"),
					JOptionPane.WARNING_MESSAGE
			);
	    }
	};
	return createButton(stopAction);
    }

    /**
     * Creates the button.
     *
     * @param a the a
     * @return the j button
     */
    public JButton createButton(Action a) {
	JButton b = new JButton();
	// setting the following client property informs the button to show
	// the action text as it's name. The default is to not show the
	// action text.
	b.putClientProperty("displayActionText", Boolean.TRUE);
	b.setAction(a);
	return b;
    }


    /**
     * The Class MyTextArea.
     */
    class MyTextArea extends JTextArea {
        
        /**
         * Instantiates a new my text area.
         */
        public MyTextArea() {
            super(null, 0, 0);
//	    setEditable(false);
	    setText("");
        }

        /* (non-Javadoc)
         * @see javax.swing.JComponent#getAlignmentX()
         */
        @Override
		public float getAlignmentX () {
            return LEFT_ALIGNMENT;
        }
 
        /* (non-Javadoc)
         * @see javax.swing.JComponent#getAlignmentY()
         */
        @Override
		public float getAlignmentY () {
            return TOP_ALIGNMENT;
        }
    }
}


