package edu.nju.controller.msgqueue.operation;

import javax.swing.JFrame;

import edu.nju.view.RecordDialog;
/**
 * 
 * @author czq
 *
 */
public class RecordOperation extends MineOperation {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1530254459995701485L;
	private JFrame frame;
	public RecordOperation(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void execute() {
		RecordDialog record = new RecordDialog(frame);
		record.show();
	}

}
