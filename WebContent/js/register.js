function judgeInput()
{
	var username = document.getElementById("input_username").value;
	var password = document.getElementById("input_password").value;
	var pwdconfirm = document.getElementById("input_pwdconfirm").value;

	var obj_usernameErrorMsg = document.getElementById("p_username_errormsg");
	var obj_pwdErrorMsg = document.getElementById("p_password_errormsg");
	var obj_pwdConfirmErrorMsg = document.getElementById("p_passwordconfirm_errormsg");

	var flag = true;
	
	if(username.length == 0)
	{
		flag = false;
		obj_usernameErrorMsg.innerHTML = "用户名不能为空";
	}
	else
		obj_usernameErrorMsg.innerHTML = "";
	if(password.length == 0)
	{
		flag = false;
		obj_pwdErrorMsg.innerHTML = "密码不能为空";
	}
	else
		obj_pwdErrorMsg.innerHTML = "";
	if(pwdconfirm.length == 0)
	{
		flag = false;
		obj_pwdConfirmErrorMsg.innerHTML = "请再输入一次密码";
	}
	else if(pwdconfirm != password)
	{
		flag = false;
		obj_pwdConfirmErrorMsg.innerHTML = "两次密码输入不一致，请确认";
	}
	else
		obj_pwdConfirmErrorMsg.innerHTML = "";
	return flag;
}