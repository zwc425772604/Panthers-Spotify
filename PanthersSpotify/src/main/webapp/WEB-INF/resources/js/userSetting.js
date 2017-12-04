function sendUserSetting()
{
	var privacy = $("#privacy :selected").text();
	var lang = $("#lang :selected").text();
	$.ajax({
        url: "${cp}/../editUserSetting",
        type: "POST",
        asyn: false,
        data: {"privacy": privacy, "lang":lang},
        cache: false,
        success : function(response)
        {
        	
        },
        error: function(e)
        {
          console.log(e);
        }
	});
}