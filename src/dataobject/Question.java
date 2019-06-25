package dataobject;

public class Question {
	private long questionid; // question id
	private long paperid;
	private String type; // radio, check, text; references to question_type 
	private String question;

	public long getQuestionid() { return questionid; }
	public void setQuestionid(long questionid) { this.questionid = questionid; }

	public long getPaperid() { return paperid; }
	public void setPaperid(long paperid) { this.paperid = paperid; }

	public String getType() { return type; }
	public void setType(String type) { this.type = type; }

	public String getQuestion() { return question; }
	public void setQuestion(String question) { this.question = question; }
}
