$(document).ready(function(){
      //hide all other containers in <div id= 'middle-content'> beside except overview_container
      $("#overview_container").siblings().hide();
     
    });

w3.includeHTML();
function displayContainer(name)
{
  var n = "#" + name;
  $(n).siblings().hide();
  $(n).show();
}