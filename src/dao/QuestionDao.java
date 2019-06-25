package dao;

import dataobject.*;
import util.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class QuestionDao {
	private Question question;
	private static Connection connection;
	
	/*
	 * init the QuestionDao, with initializing the question inside it with all value default
	 */
	public QuestionDao() {
		if (connection == null)
			connection = Conn.getConnection();
		question = new Question();
	}
	
	public QuestionDao(Long questionid) {
		if (connection == null)
			connection = Conn.getConnection();
		question = new Question();
		this.setQuestion(questionid);
	}
	
	/*
	 * init the QuestionDao, with initializeing the question inside it with @parameter q
	 */
	public QuestionDao(Question q) {
		this();
		this.setQuestion(q);
	}
	
	/*
	 * get the question inside the QuestionDao
	 */
	public Question getQuestion() {
		return this.question;
	}
	
	/*
	 * set the question inside the QuestionDao to @parameter quetion
	 */
	public Question setQuestion(Question question) {
		return this.question = question;
	}
	
	/*
	 * set the question inside the QuestionDao to question found from
	 * database by @parameter questionid 
	 */
	public Question setQuestion(Long questionid) {
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			state = connection.prepareStatement(
					"select questionid, paperid, question_type, question from q_question where questionid = ?");
			state.setLong(1, questionid);
			rs = state.executeQuery();
			if (rs.next()) {
				question.setQuestionid(rs.getLong("questionid"));
				question.setPaperid(rs.getLong("paperid"));
				question.setQuestion(rs.getString("question"));
				question.setType(rs.getString("question_type"));
			} else {
				question = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-6);
		}
		return this.question;
	}
	
//	/*
//	 * add a new question to database, the specified questionid property of question
//	 * will be ignored, after adding, the question inside the QuestionDao will be set
//	 * to @parameter question, with accurate questionid in database;
//	 * take care that you **can not add two question be same on question content and queston_type
//	 * under the same paper**;
//	 */
//	public boolean addQuestion(Question question) {
//		PreparedStatement state = null;
//		ResultSet rs = null;
//		int result = 0;
//		try {
//			state = connection.prepareStatement(
//					"insert into q_question (questionid, paperid, question_type, question) values (?, ?, ?, ?);");
//			state.setNull(1, java.sql.Types.INTEGER);
//			state.setLong(2, question.getPaperid());
//			state.setString(3, question.getType());
//			state.setString(4, question.getQuestion());
//			result = state.executeUpdate();
//			if (result == 0) {
//				return false;
//			}
//			
//			// get the question id from database;
//			state = connection.prepareStatement(
//					"select questionid from q_question where paperid = ? and question_type = ? and question = ?;");
//			state.setLong(1, question.getPaperid());
//			state.setString(2, question.getType());
//			state.setString(3, question.getQuestion());
//			rs = state.executeQuery();
//
//			rs.next();
//			question.setQuestionid(rs.getLong("questionid"));
//			this.question = question;
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.exit(-7);
//		}
//		return true;
//	}
	
	/*
	 * add a new question to database, using the question inside the QuestionDao
	 */
	public boolean addQuestion() {
		return this.addQuestion(question.getPaperid(), question.getType(),
				question.getQuestion());
	}
	
	/*
	 * you can just pass paperid, question type, question content as parameters
	 */
	public boolean addQuestion(Long paperid, String questionType, String questionContent) {
		PreparedStatement state = null;
		ResultSet rs = null;
		int result = 0;
		try {
			state = connection.prepareStatement(
					"insert into q_question (questionid, paperid, question_type, question) values (?, ?, ?, ?);");
			state.setNull(1, java.sql.Types.INTEGER);
			state.setLong(2, paperid);
			state.setString(3, questionType);
			state.setString(4, questionContent);
			result = state.executeUpdate();
			if (result == 0) {
				return false;
			}
			
			// get the question id from database;
			state = connection.prepareStatement(
					"select questionid from q_question where paperid = ? and question_type = ? and question = ?;");
			state.setLong(1, paperid);
			state.setString(2, questionType);
			state.setString(3, questionContent);
			rs = state.executeQuery();

			rs.next();
			question.setQuestionid(rs.getLong("questionid"));
			question.setPaperid(paperid);
			question.setType(questionType);
			question.setQuestion(questionContent);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-7);
		}
		return true;
	}
	
	/*
	 * you can update the question_type and the question content,
	 * do not change anything else;
	 */
	public boolean updateQuestion() {
		PreparedStatement state = null;
		int result = 0;
		try {
			state = connection.prepareStatement(
					"update q_question set question_type = ?, question = ? where questionid = ?;");
			state.setString(1, question.getType());
			state.setString(2, question.getQuestion());
			state.setLong(3, question.getQuestionid());
			result = state.executeUpdate();
		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		}
		return result != 0;
	}
	
	/*
	 * delete question specific by quiestionid
	 */
	public static boolean deleteQuestion(Long questionid) {
		PreparedStatement state = null;
		int result = 0;
		try {
			state = connection.prepareStatement(
					"call delete_question(?);");
			state.setLong(1, questionid);
			result = state.executeUpdate();
		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		}
		return result != 0;
	}
	
	/*
	 * answer the question, add an answer to database,
	 * you can just pass answer content and the uid of the user 
	 * who did this answer.
	 */
	public boolean addAnswer(String answerContent, Long respondentId) {
		PreparedStatement state = null;
		PreparedStatement stCheckDone = null;
		ResultSet rs = null;
		boolean addFlag = true;
		try {
			state = connection.prepareStatement(
					"insert into q_answer (questionid, answer, respondent) values (?, ?, ?);");
			state.setLong(1, question.getQuestionid());
			state.setString(2, answerContent);
			state.setLong(3, respondentId);
			if (state.executeUpdate() == 0) {
				addFlag = false;
			}
			
			
			stCheckDone = connection.prepareStatement(
					"select count(*) as cnt from q_done_answer where uid = ? and paperid = ?;");
			stCheckDone.setLong(1, respondentId);
			stCheckDone.setLong(2, question.getPaperid());
			rs = stCheckDone.executeQuery();
			rs.next();
			if (rs.getLong("cnt") == 0) {
				stCheckDone = connection.prepareStatement(
						"insert into q_done_answer (uid, paperid) values (?, ?);");
				stCheckDone.setLong(1, respondentId);
				stCheckDone.setLong(2, question.getPaperid());
				stCheckDone.executeUpdate();
			}
		} catch (SQLException e) {
			addFlag = false;

			System.out.println("Unknown SQLException");
			e.printStackTrace();
			System.exit(-12);
		}
		return addFlag;
	}

	/*
	 * get all answers belong to this question.
	 */
	public ArrayList<Answer> getAllAnswers() {
		ArrayList<Answer> answers = new ArrayList<Answer>();
		ResultSet rs = null;
		PreparedStatement state = null;
		try {
			state = connection.prepareStatement(
					"select answerid, questionid, answer, respondent from q_answer where questionid = ?;");
			state.setLong(1, question.getQuestionid());
			rs = state.executeQuery();
			while (rs.next()) {
				Answer ans = new Answer();
				ans.setAnswerid(rs.getLong("answerid"));
				ans.setQuestionid(rs.getLong("questionid"));
				ans.setAnswer(rs.getString("answer"));
				ans.setRespondent(rs.getLong("respondent"));
				answers.add(ans);
			}
		} catch (SQLException e) {
			System.out.println("Unknown SQLException!");
			e.printStackTrace();
			System.exit(-8);
		}
		return answers;
	}
	
	/*
	 * get the specific answers belong to the question inside QuestionDao,
	 * means you can get the specific user's answer. 
	 */
	public ArrayList<Answer> getSpecificAnswers(Long respondentId) {
		ArrayList<Answer> answers = new ArrayList<Answer>();
		ResultSet rs = null;
		PreparedStatement state = null;
		try {
			state = connection.prepareStatement(
					"select answerid, questionid, answer from q_answer where questionid = ? and respondent = ?;");
			state.setLong(1, question.getQuestionid());
			state.setLong(2, respondentId);
			rs = state.executeQuery();
			while (rs.next()) {
				Answer ans = new Answer();
				ans.setAnswerid(rs.getLong("answerid"));
				ans.setQuestionid(rs.getLong("questionid"));
				ans.setAnswer(rs.getString("answer"));
				ans.setRespondent(respondentId);
				answers.add(ans);
			}
		} catch (SQLException e) {
			System.out.println("Unknown SQLException!");
			e.printStackTrace();
			System.exit(-8);
		}
		return answers;
	}
	
	/*
	 * you can call this function only when the question is 'radio'(single select) or 
	 * 'check'(multiple select), if not, the result will be null or undefined.
	 */
	public TreeMap<Selection, Integer> analyzeSelection() {
		TreeMap<Selection, ArrayList<Answer>> tempRes = 
				new TreeMap<Selection, ArrayList<Answer>>(new Comparator<Selection>() {
					public int compare(Selection a, Selection b) {
						return (int)(a.getSelectionid() - b.getSelectionid());
					}
				});
		ArrayList<Selection> selections = this.getSelection();
		ArrayList<Answer> answers = this.getAllAnswers();
		for (int i = 0; i < selections.size(); ++i) {
			tempRes.put(selections.get(i), new ArrayList<Answer>());
		}
		try {
			for (int i = 0; i < answers.size(); ++i) {
				tempRes.get(Selection.parseSelection(
						Long.parseLong(answers.get(i).getAnswer())
						)).add(answers.get(i));
			}
		} catch (NumberFormatException e) {
			tempRes = null;
			e.printStackTrace();
			return null;
		}
		TreeMap<Selection, Integer> anaResult =
				new TreeMap<Selection, Integer>(new Comparator<Selection>() {
					public int compare(Selection a, Selection b) {
						return (int)(a.getSelectionid() - b.getSelectionid());
					}
				});
		Iterator<Entry<Selection, ArrayList<Answer>>> it = tempRes.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Selection, ArrayList<Answer>> mapIt = it.next();
			anaResult.put(mapIt.getKey(), mapIt.getValue().size());
		}
		return anaResult;
	}
	
	/*
	 * get all selection for this question,
	 * take care you should not use this function if this question with a random text answer.
	 */
	public ArrayList<Selection> getSelection() {
		ArrayList<Selection> selections = new ArrayList<Selection>();
		ResultSet rs = null;
		PreparedStatement state = null;
		try {
			state = connection.prepareStatement(
					"select selectionid, questionid, selection_describe from q_selection where questionid = ?");
			state.setLong(1, question.getQuestionid());
			rs = state.executeQuery();
			while (rs.next()) {
				Selection sele = new Selection();
				sele.setSelectionid(rs.getLong("selectionid"));
				sele.setQuestionid(rs.getLong("questionid"));
				sele.setSelection_describe(rs.getString("selection_describe"));
				selections.add(sele);
			}
		} catch (SQLException e) {
			System.out.println("Unknown SQLException!");
			e.printStackTrace();
			System.exit(-9);
		}
		return selections;	
	}
	
//	/*
//	 * add a selection to the question inside the QuestionDao
//	 */
//	public boolean addSelection(Selection selection) {
//		PreparedStatement state = null;
//		boolean addFlag = true;
//		try {
//			state = connection.prepareStatement(
//					"insert into q_selection (questionid, selection_describe) values (?, ?);");
//			state.setLong(1, selection.getQuestionid());
//			state.setString(2, selection.getSelection_describe());
//			if (state.executeUpdate() == 0) {
//				addFlag = false;
//			}
//		} catch (SQLException e) {
//			addFlag = false;
//
//			System.out.println("Unknown SQLException");
//			e.printStackTrace();
//			System.exit(-12);
//		}
//		return addFlag;
//	}
	
	/*
	 * add a selection to the question inside the QuestionDao,
	 * you can just pass a String as parameter
	 */
	public boolean addSelection(String selectionDesc) {
		PreparedStatement state = null;
		boolean addFlag = true;
		try {
			state = connection.prepareStatement(
					"insert into q_selection (questionid, selection_describe) values (?, ?);");
			state.setLong(1, question.getQuestionid());
			state.setString(2, selectionDesc);
			if (state.executeUpdate() == 0) {
				addFlag = false;
			}
		} catch (SQLException e) {
			addFlag = false;

			System.out.println("Unknown SQLException");
			e.printStackTrace();
			System.exit(-12);
		}
		return addFlag;
	}
	
//	/*
//	 * update the selection with the same selectionid with @parameter selection
//	 */
//	public boolean updateSelection(Selection selection) {
//		PreparedStatement state = null;
//		int result = 0;
//		try {
//			state = connection.prepareStatement(
//					"update q_selection set selection_describe = ?, questionid = ? where selectionid = ?;");
//			state.setString(1, selection.getSelection_describe());
//			state.setLong(2, selection.getSelectionid());
//			state.setLong(3, selection.getSelectionid());
//			result = state.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		if (result == 0) {
//			return false;
//		} else {
//			return true;
//		}
//	}
	
	/*
	 * update the selection describe
	 */
	public static boolean updateSelection(Long selectionid, String selectionDescribe) {
		PreparedStatement state = null;
		int result = 0;
		try {
			state = connection.prepareStatement(
					"update q_selection set selection_describe = ? where selectionid = ?;");
			state.setString(1, selectionDescribe);
			state.setLong(2, selectionid);
			result = state.executeUpdate();
		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		}
		return result != 0;
	}
	
	/*
	 * delete the selection specific by selection id
	 */
	public static boolean deleteSelection(Long selectionid) {
		PreparedStatement state = null;
		int result = 0;
		try {
			state = connection.prepareStatement(
					"delete from q_selection where selectionid = ?;");
			state.setLong(1, selectionid);
			result = state.executeUpdate();
		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		}
		return result != 0;
	}
	
//	/*
//	 * answer the question, add an answer to database,
//	 * the property answerid will be ignored
//	 */
//	public boolean addAnswer(Answer ans) {
//		PreparedStatement state = null;
//		boolean addFlag = true;
//		try {
//			state = connection.prepareStatement(
//					"insert into q_answer (questionid, answer, respondent) values (?, ?, ?);");
//			state.setLong(1, ans.getQuestionid());
//			state.setString(2, ans.getAnswer());
//			state.setLong(3, ans.getRespondent());
//			if (state.executeUpdate() == 0) {
//				addFlag = false;
//			}
//		} catch (SQLException e) {
//			addFlag = false;
//
//			System.out.println("Unknown SQLException");
//			e.printStackTrace();
//			System.exit(-12);
//		}
//		return addFlag;
//	}
}