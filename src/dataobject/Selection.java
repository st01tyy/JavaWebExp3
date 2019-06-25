package dataobject;

public class Selection {
	private long selectionid;
	private long questionid;
	private String selection_describe;

	public long getSelectionid() { return selectionid; }
	public void setSelectionid(long selectionid) { this.selectionid = selectionid; }

	public long getQuestionid() { return questionid; }
	public void setQuestionid(long questionid) { this.questionid = questionid; }

	public String getSelection_describe() { return selection_describe; }
	public void setSelection_describe(String selection_describe) { this.selection_describe = selection_describe; }
	
	public static Selection parseSelection(Long selectionid) {
		Selection blankSelection = new Selection();
		blankSelection.setSelectionid(selectionid);
		return blankSelection;
	}
}
