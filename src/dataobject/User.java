package dataobject;

public class User {
	private long uid;
	private Boolean admin;
	private String username;
	private String passwd;
	private String nickname;

	public long getUid() { return uid; }
	public void setUid(long uid) { this.uid = uid; }
	
	public boolean isAdmin() { return admin; }
	public void setAdmin(boolean ad) { this.admin = ad; }

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public String getPasswd() { return passwd; }
	public void setPasswd(String passwd) { this.passwd = passwd; }

	public String getNickname() { return nickname; }
	public void setNickname(String nickname) { this.nickname = nickname; }
}
