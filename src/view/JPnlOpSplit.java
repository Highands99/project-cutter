package view;

import javax.swing.JPanel;

import split.Splitter;

public abstract class JPnlOpSplit extends JPanel {
	private static final long serialVersionUID = 4L;
	
	public JPnlOpSplit() {
		this.initialize();
		this.setAllEnabled(false);
	}
	
	public JPnlOpSplit(String s) {	
		this.initialize();
		this.setSplitter(s);
	}
	
	protected abstract void initialize();
	
	public abstract Splitter getSplitter();
	
	public abstract void setSplitter(Splitter s);
	
	public abstract void setSplitter(String s);
	
	protected abstract void setAllEnabled(boolean flag);

	public static boolean isNumeric(final String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        
        return true;
    }
}
