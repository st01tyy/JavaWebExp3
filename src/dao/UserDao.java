package dao;

import dataobject.*;
import util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserDao {
	private User user;
	private static Connection connection;
	
	/*
	 * init the UserDao class, and init a blank user inside it.
	 */
	public UserDao() {
		if (connection == null)
			connection = Conn.getConnection();
		this.user = new User();
	}
	
	/*
	 * init the UserDao class, and init a user with User passed in;
	 */
	public UserDao(User u) { 
		if (connection == null)
			connection = Conn.getConnection();
		this.setUser(u);
	}

	/*
	 * init the UserDao, using uid to init the user inside;
	 */
	public UserDao(Long uid) {
		if (connection == null)
			connection = Conn.getConnection();
		this.user = new User();
		this.setUser(uid);
	}
	
	/*
	 * init the UserDao, using username to init the user inside
	 */
	public UserDao(String username) {
		if (connection == null)
			connection = Conn.getConnection();
		this.user = new User();
		this.setUser(username);
	}
	
	/*
	 * get the user inside UserDao
	 */
	public User getUser() {
		return this.user;
	}
	
	/*
	 * set a new user to UserDao
	 */
	public void setUser(User u) {
		this.user = u;
	}
	
	/*
	 * set a new user with username, the user inside will be blank if failed.
	 */
	public boolean setUser(String username) {
		PreparedStatement state = null;
		ResultSet rs = null;
		boolean setFlag = false;
		try {
			state = connection.prepareStatement(
					"select uid, username, passwd, nickname, admin from q_user where username = ?;");
			state.setString(1, username);
			rs = state.executeQuery();

			if (rs.next()) {
				this.user.setUid(rs.getLong("uid"));
				this.user.setUsername(rs.getString("username"));
				this.user.setNickname(rs.getString("nickname"));
				this.user.setAdmin(rs.getBoolean("admin"));
				this.user.setPasswd(rs.getString("passwd"));
				setFlag = true;
			} else {
				setFlag = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return setFlag;
	}
	
	/*
	 * set user by uid
	 */
	public boolean setUser(long uid) {
		PreparedStatement state = null;
		ResultSet rs = null;
		boolean setFlag = false;
		try {
			state = connection.prepareStatement(
					"select uid, username, passwd, nickname, admin from q_user where uid = ?;");
			state.setLong(1, uid);
			rs = state.executeQuery();

			if (rs.next()) {
				this.user.setUid(rs.getLong("uid"));
				this.user.setUsername(rs.getString("username"));
				this.user.setNickname(rs.getString("nickname"));
				this.user.setAdmin(rs.getBoolean("admin"));
				this.user.setPasswd(rs.getString("passwd"));
				setFlag = true;
			} else {
				setFlag = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return setFlag;
	}
	
	/*
	 * check if the username and passwd accurate, if accurate, the user inside
	 * UserDao will be set to the user checked in database
	 */
	public boolean checkUser(String username, String passwd) {
		boolean checkFlag = false;
		this.user = new User();
		this.user.setUsername(username);
		this.user.setPasswd(passwd);
		PreparedStatement state = null;
		
		try {
			state = connection.prepareStatement("call check_user_passwd(?, ?);");
			state.setString(1, this.user.getUsername());
			state.setString(2, passwd);
			ResultSet rs = state.executeQuery();

			if (rs.next() && rs.getLong("result") == 1) {
				state = connection.prepareStatement(
						"select uid, username, passwd, nickname, admin from q_user where username = ?;");
				state.setString(1, this.user.getUsername());
				rs = state.executeQuery();

				if (rs.next()) {
					this.user.setUid(rs.getLong("uid"));
					this.user.setNickname(rs.getString("nickname"));
					this.user.setAdmin(rs.getBoolean("admin"));
					this.user.setPasswd(rs.getString("passwd"));
					checkFlag = true;
				} else {
					checkFlag = false;
				}
			} else {
				checkFlag = false;
			}
		} catch (SQLException e) {
			System.out.println("Unknown SQLException!");
			e.printStackTrace();
			System.exit(-3);
		}

		return checkFlag;
	}
	
//	/*
//	 * add a new user to database with the user passed in as parameter;
//	 * if succeed, the user inside UserDao will be set to the user just added.
//	 * if failed, the user inside will not change.
//	 */
//	public boolean addUser(User user) {
//		boolean addResult = true;
//		PreparedStatement test = null;
//		PreparedStatement state = null;
//		try {
//			// if username already exists
//			test = connection.prepareStatement("select * from q_user where username = ?");
//			test.setString(1,  user.getUsername());
//			if (test.executeQuery().next()) {
//				addResult = false;
//			} else {
//				state = connection.prepareStatement(
//						"insert into q_user (uid, username, passwd, nickname, admin) values (default, ?, ?, ?, ?);");
//				state.setString(1, user.getUsername());
//				state.setString(2, user.getPasswd());
//				state.setString(3, user.getNickname());
//				state.setBoolean(4, user.isAdmin());
//				state.executeUpdate();
//				
//				// reset uid and other thing;
//				this.checkUser(user.getUsername(), user.getPasswd());
//				addResult = true;
//			}
//		} catch (SQLException e) {
//			System.out.println("Unknown SQLException!");
//			e.printStackTrace();
//			System.exit(-4);
//		}
//		return addResult;
//	}
	
	/*
	 * add a new user to database, using the user inside UserDao.
	 * if succeed, the user inside UserDao will be set to the user just added.
	 * if failed, the user inside will not change.
	 */
	public boolean addUser() {
		return this.addUser(user.getUsername(), user.getPasswd(), user.getNickname(),
				user.isAdmin());
	}
	
	/*
	 * you can just pass username, password, nickname and boolean admin as parameter;
	 */
	public boolean addUser(String username, String passwd, String nickname, boolean admin) {
		boolean addResult = true;
		PreparedStatement test = null;
		PreparedStatement state = null;
		try {
			// if username already exists
			test = connection.prepareStatement("select * from q_user where username = ?");
			test.setString(1,  user.getUsername());
			if (test.executeQuery().next()) {
				addResult = false;
			} else {
				state = connection.prepareStatement(
						"insert into q_user (uid, username, passwd, nickname, admin) values (default, ?, ?, ?, ?);");
				state.setString(1, username);
				state.setString(2, passwd);
				state.setString(3, nickname);
				state.setBoolean(4, admin);
				state.executeUpdate();
				
				// reset uid and other thing;
				this.checkUser(username, passwd);
				addResult = true;
			}
		} catch (SQLException e) {
			System.out.println("Unknown SQLException!");
			e.printStackTrace();
			System.exit(-4);
		}
		return addResult;
	}
	
	/*
	 * update the user's information, using the user inside UserDao,
	 * you can update everything except the uid.
	 */
	public boolean updateUser() {
		PreparedStatement state = null;
		long result = 0;
		try {
			state = connection.prepareStatement("update q_user set username = ?, admin = ?, passwd = ?, nickname = ? where uid = ?");
			state.setString(1, user.getUsername());
			state.setBoolean(2, user.isAdmin());
			state.setString(3, user.getPasswd());
			state.setString(4, user.getNickname());
			state.setLong(5, user.getUid());
			result = state.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update user failed!");
			e.printStackTrace();
		}
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/*
	 * delete the user specific by username in database;
	 */
	public boolean deleteUser(String username) {
		PreparedStatement state = null;
		int result = 0;
		try {
			state = connection.prepareStatement(
					"call delete_user(?);");
			state.setString(1, username);
			result = state.executeUpdate();
		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		}
		return result != 0;
	}
	
	public static ArrayList<Paper> getAllPapers() {
		ArrayList<Paper> allPapers = new ArrayList<Paper>();
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			state = connection.prepareStatement(
					"select paperid, uid, paper_name, publish_time, cutoff_time from q_paper;");
			rs = state.executeQuery();
			while (rs.next()) {
				Paper paper = new Paper();
				paper.setPaperid(rs.getLong("paperid"));
				paper.setPapername(rs.getString("paper_name"));
				paper.setOwnerid(rs.getLong("uid"));
				paper.setPublish_time(rs.getTimestamp("publish_time"));
				paper.setCutoff_time(rs.getTimestamp("cutoff_time"));
				allPapers.add(paper);
			}
		} catch (SQLException e) {
			System.out.println("Unknown SQLException!");
			e.printStackTrace();
			System.exit(-1);
		}
		return allPapers;
	}
	
	/*
	 * retrun all papers own to the user inside UserDao.
	 */
	public ArrayList<Paper> getOwnPapers() {
		ArrayList<Paper> ownPapers = new ArrayList<Paper>();
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			state = connection.prepareStatement(
					"select paperid, paper_name, publish_time, cutoff_time from q_paper where uid = ?");
			state.setLong(1, user.getUid());
			rs = state.executeQuery();
			while (rs.next()) {
				Paper paper = new Paper();
				paper.setPaperid(rs.getLong("paperid"));
				paper.setPapername(rs.getString("paper_name"));
				paper.setOwnerid(user.getUid());
				paper.setPublish_time(rs.getTimestamp("publish_time"));
				paper.setCutoff_time(rs.getTimestamp("cutoff_time"));
				ownPapers.add(paper);
			}
		} catch (SQLException e) {
			System.out.println("Unknown SQLException!");
			e.printStackTrace();
			System.exit(-1);
		}
		return ownPapers;
	}
	
	/*
	 * return all papers allowed to do by the user inside UserDao,
	 * means the papaer not allowed to do on the current time will not be shown
	 */
	public ArrayList<Paper> getAllowedPaper() {
		PreparedStatement state = null;
		ArrayList<Paper> allowedPaper = new ArrayList<Paper>();
		ResultSet rs = null;
		try {
			state = connection.prepareStatement(
					"select paperid, uid, paper_name, publish_time, cutoff_time " +
					"from q_paper where paperid not in " +
					"(select paperid from q_done_answer where uid = ?) " +
					" and NOW() between publish_time and cutoff_time;");
// 					"select q_paper.paperid as paperid, q_paper.uid as uid, paper_name, publish_time, cutoff_time " +
// 					"from q_paper join q_allow_answer on q_paper.paperid = q_allow_answer.paperid " +
// 					"where q_allow_answer.uid = ? and NOW() between publish_time and cutoff_time;");
			state.setLong(1, user.getUid());
			rs = state.executeQuery();
			while (rs.next()) {
				Paper p = new Paper();
				p.setPaperid(rs.getLong("paperid"));
				p.setOwnerid(rs.getLong("uid"));
				p.setPapername(rs.getString("paper_name"));
				p.setPublish_time(rs.getTimestamp("publish_time"));
				p.setCutoff_time(rs.getTimestamp("cutoff_time"));
				allowedPaper.add(p);
			}
		} catch (SQLException e) {
			System.out.println("Unknown SQLException!");
			e.printStackTrace();
			System.exit(-11);
		}
		
		return allowedPaper;
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		// test all the methods in UserDao
		UserDao ud1 = new UserDao();
		ud1.setUser(1);
		// ud1.addUser("add_user1", "test", "add_user1", false);
		// ud1.getUser().setNickname("add_user1_after_update");
		// ud1.getUser().setAdmin(true);
		// ud1.updateUser();
		
		PaperDao pd1 = new PaperDao();
		pd1.addPaper(ud1.getUser().getUid(), "added_paper_to_added_user1",
				java.sql.Timestamp.valueOf("2019-06-01 00:00:00"),
				java.sql.Timestamp.valueOf("2019-06-20 00:00:00"));
		pd1.getPaper().setPapername("updated_paper_to_added_user1");
		pd1.updatePaper();
		pd1.addAllowUser(new UserDao("leafee").getUser().getUid());
		QuestionDao qd1 = new QuestionDao();
		QuestionDao qd2 = new QuestionDao();
		
		qd1.addQuestion(pd1.getPaper().getPaperid(), "radio", "added_question_radio_to_added_paper1");
		qd2.addQuestion(pd1.getPaper().getPaperid(), "check", "added_question_check_to_added_paper2");
		qd1.addSelection("selection11_of_question1");
		qd1.addSelection("selection12_of_question1");
		qd2.addSelection("selection21_of_question2");
		qd2.addSelection("selection22_of_question2");
		qd1.getQuestion().setQuestion("updated_question_radio_to_added_paper1");
		qd1.updateQuestion();
		
		ArrayList<Selection> selOfq1 = qd1.getSelection();
		ArrayList<Selection> selOfq2 = qd2.getSelection();
		qd1.addAnswer(String.valueOf(selOfq1.get(0).getSelectionid()), new UserDao("leafee").getUser().getUid());
		qd2.addAnswer(String.valueOf(selOfq2.get(0).getSelectionid()), new UserDao("leafee").getUser().getUid());
		qd2.addAnswer(String.valueOf(selOfq2.get(1).getSelectionid()), new UserDao("leafee").getUser().getUid());

		ArrayList<Paper> arrPaper = ud1.getOwnPapers();
		for (int i = 0; i < arrPaper.size(); ++i) {
			System.out.println(arrPaper.get(i).getPapername());
		}
		System.out.println("============");
		arrPaper = ud1.getAllowedPaper();
		for (int i = 0; i < arrPaper.size(); ++i) {
			System.out.println(arrPaper.get(i).getPapername());
		}
		System.out.println("============");
		arrPaper = UserDao.getAllPapers();
		for (int i = 0; i < arrPaper.size(); ++i) {
			System.out.println(arrPaper.get(i).getPapername());
		}
		System.out.println("============");
		System.out.println("============");
		System.out.println("============");

		for (User u : pd1.getAllowedUser()) {
			System.out.println(u.getNickname());
		}
		System.out.println("============");
		
//		for (Paper p : arrPaper) {
//			for (Question q : new PaperDao(p.getPaperid()).getQuestion()) {
//				System.out.println(q.getQuestion());
//				QuestionDao qdd = new QuestionDao(q.getQuestionid());
//				// for (Selection s : qdd.getSelection()) {
//				// 	System.out.println(s.getSelection_describe());
//				// }
//				// for (Answer a : qdd.getAllAnswers()) {
//				// 	System.out.println(a.getAnswer());
//				// }
//				TreeMap<Selection, ArrayList<Answer>> qatm = qdd.analyzeSelection();
//				Iterator<Entry<Selection, ArrayList<Answer>>> it = qatm.entrySet().iterator();
//				while (it.hasNext()) {
//					Map.Entry<Selection, ArrayList<Answer>> mapIt = it.next();
//					System.out.print(mapIt.getKey().getSelection_describe() + " ::: ");
//					System.out.println(mapIt.getValue().size());
//				}
//				
//				ArrayList<Answer> speAns = qdd.getSpecificAnswers(new UserDao("leafee").getUser().getUid());
//				System.out.println("The User " + ud1.getUser().getNickname() + " 's Answer:");
//				for (Answer a : speAns) {
//					System.out.println(a.getAnswer());
//				}
//					
//			}
//		}
		System.out.println("=============");

		for (Paper p : arrPaper) {
			for (Question q : new PaperDao(p.getPaperid()).getQuestion()) {
				QuestionDao qdd = new QuestionDao(q.getQuestionid());
				Selection s = qdd.getSelection().get(0);
				System.out.println(s.getSelection_describe());
				QuestionDao.updateSelection(s.getSelectionid(), "Updated Selection");
				QuestionDao.deleteSelection(s.getSelectionid());
				break;
			}
			break;
		}
		QuestionDao.deleteQuestion(qd1.getQuestion().getQuestionid());
		PaperDao.deletePaper(pd1.getPaper().getPaperid());
		ud1.deleteUser("add_user1");
		input.close();
	}
}
