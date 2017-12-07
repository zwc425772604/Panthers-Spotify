function insertFriends(data)
{
  $("#userFriendsList").empty();
  for (var i = 0; i < data.length; i++)
  {
	var photoUrl = data[i]['userPhotoUrl'];
	if(!photoUrl){
		photoUrl = "https://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg";
	}
	else{
		photoUrl = "${cp}/../resources/data" + photoUrl;
	}
    $("#userFriendsList").append([
      '<div class="right-col-section row">',
        '<div>',
        '<img src = "' + photoUrl +'" class="rounded-circle right-col-image"',
        'alt = "Generic placeholder thumbnail"></div>',
        '<div class="right-col-desp"><div class = "right-col-friends-name">' + data[i]['username'] + '<span style="display:none;" class="friends-email">' + data[i]['userID'] + '</span>',
        '</div></div></div>'
    ].join(''));
  }
}