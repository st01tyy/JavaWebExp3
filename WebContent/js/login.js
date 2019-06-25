function judgeInput()
{
	/*
		在提交表单前对用户输入的用户名、密码的合法性进行判断。
		若不合法则阻止表单的提交并显示相关提示。
	*/
	var username = document.getElementById("input_username");
	var password = document.getElementById("input_password");
	var userNameErrorMsg = document.getElementById("p_username_errormsg");
	var passwordErrorMsg = document.getElementById("p_password_errormsg");
	var flag = true;
	if(username.value.length == 0)
	{
		userNameErrorMsg.innerHTML = "用户名不能为空";
		flag = false;
	}
	else
		userNameErrorMsg.innerHTML = "";
	if(password.value.length == 0)
	{
		passwordErrorMsg.innerHTML = "密码不能为空";
		flag = false;
	}
	else
		passwordErrorMsg.innerHTML = "";
	return flag;
}