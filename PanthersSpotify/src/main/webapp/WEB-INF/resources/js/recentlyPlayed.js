$(document).ready(function(){
	var email = "himself";
	$.ajax({
        url: "${cp}/../getUserHistory",
        type: "POST",
        asyn: false,
        data: {"email" : email},
        cache: true,
        success : function(response)
        {
        	var actual_JSON = JSON.parse(response);
        	insertSongsTable(actual_JSON);
        },
        error: function(e)
        {
          console.log(e);
        }
      });
});


function insertSongsTable(data)
{
	var num = data.length;
	for(i = 0; i < num; i++){
		var songTitle = data[i]['songTitle'];
		$("#song-table").find('tbody').append([
			'<tr>',	
			  '<td> </td>',
			  '<td>' + songTitle + '</td>',
			  '<td> </td>',
			  '<td> </td>',
			 '</tr>'
		].join(''));
	}
}
