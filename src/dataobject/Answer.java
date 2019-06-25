package dataobject;

public class Answer {
	private long answerid;
	private long questionid;
	private long respondent;
	private String answer;

	public long getAnswerid() { return answerid; }
	public void setAnswerid(long answerid) { this.answerid = answerid; }

	public long getQuestionid() { return questionid; }
	public void setQuestionid(long questionid) { this.questionid = questionid; }

	public long getRespondent() { return respondent; }
	public void setRespondent(long respondent) { this.respondent = respondent; }

	public String getAnswer() { return answer; }
	public void setAnswer(String answer) { this.answer = answer; }
}
