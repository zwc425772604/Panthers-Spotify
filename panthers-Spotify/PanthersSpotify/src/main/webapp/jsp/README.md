# PantherGUI

## Changing partial content when pressing a button/nav without refreshing the page

### In HTML:
- have a 
```<div id="container"> content you would like to update </div> ```
- have a 
``` <button id="magicalbutton">PRESS ME</button> ``` 
- have a new html file containing the content you would like the container to change to.
ex: mainpage_content.html

### In Js:

Implement one function for each button:

- MAGICALBUTTON: button that makes everything happen

- CHANGING_CONTENT: the container that is changing the content.

- YOURHTML.html: html file that includes the new content.
```
$(document).ready(function(){
  $("#MAGICALBUTTON").click(function(){
    $.ajax({url:"YOURHTML.html",success:function(result){
      $("#CHANGING_CONTENT").html(result);
    }});
  });
});
```

