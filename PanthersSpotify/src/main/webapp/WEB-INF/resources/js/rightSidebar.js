function insertFriends(data)
{
  $("#userFriendsList").empty();
  for (var i = 0; i < data.length; i++)
  {
    $("#userFriendsList").append([
      '<div class="right-col-section row">',
        '<div><img src = "http://weclipart.com/gimg/0A3C841B9FA4F2C6/13099629981030824019profile.svg.hi.png" class="rounded-circle right-col-image"',
        'alt = "Generic placeholder thumbnail"></div>',
        '<div class="right-col-desp"><div class = "right-col-friends-name">' + data[i]['username'] + '<span style="display:none;" class="friends-email">' + data[i]['userID'] + '</span>',
        '</div></div></div>'
    ].join(''));
  }
}