function updateChoice(number)
{
	var choicesheet = document.getElementsByName("choicesheet")[number];
	choicesheet.value = number;
}