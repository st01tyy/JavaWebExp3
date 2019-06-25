function updateAnswer(number)
{
	var buttonList = document.getElementsByName(number);
	var answersheet = document.getElementById(number);
	answersheet.value="";
	for(var a = 0; a < buttonList.length; a++)
	{
		if(buttonList[a].checked == true)
			answersheet.value += a;
	}
}
function isFinished()
{
	var answerList = document.getElementsByClassName("answersheet");
	var errormsg = document.getElementById("errormsg");
	for(var a = 0; a < answerList.length; a++)
	{
		if(answerList[a].value == "")
		{
			errormsg.innerHTML = "问卷未完成"			
			return false;
		}
	}
	errormsg.innerHTML = "";
	return true;
}
