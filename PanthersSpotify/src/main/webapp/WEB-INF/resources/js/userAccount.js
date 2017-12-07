function validateFormInputsforAccount()
    {
      var gender = $("#gender").val();
      var firstName = $("#firstName").val();
      var lastName = $("#lastName").val();
    
      $.ajax({
          url: "${cp}/../editUserAccount",
          type: "POST",
          data : {"gender" : gender, "firstName" : firstName, "lastName" : lastName },
          asyn: true,
          cache: false,
          success : function(response)
          {
            console.log(response);         
          },
          error: function(e)
          {
            console.log(e);
          }
    
        });
        $("#main-changing-content").load("jsp/browse.jsp");
      
    
}


window.URL    = window.URL || window.webkitURL;
var elBrowse  = document.getElementById("browse"),
    elPreview = document.getElementById("preview"),
    useBlob   = false && window.URL; // set to `true` to use Blob instead of Data-URL

function readImage (file) {
  var reader = new FileReader();

  reader.addEventListener("load", function () {
    var image  = new Image();
    
    image.addEventListener("load", function () {
      var imageInfo = file.name +' '+
          image.width +'×'+
          image.height +' '+
          file.type +' '+
          Math.round(file.size/1024) +'KB';

      // Show image and info
      while (elPreview.firstChild) {
    	  elPreview.removeChild(elPreview.firstChild);
    	}
      elPreview.appendChild( this );
      elPreview.insertAdjacentHTML("beforeend", '<br>');

      if (useBlob) {
        // Free some memory
        window.URL.revokeObjectURL(image.src);
      }
    });
    image.src = useBlob ? window.URL.createObjectURL(file) : reader.result;
  });

  reader.readAsDataURL(file);  
}


elBrowse.addEventListener("change", function() {

  var files = this.files, errors = "";

  if (!files) {
    errors += "File upload not supported by your browser.";
  }

  if (files && files[0]) {

    for(var i=0; i<files.length; i++) {
      var file = files[i];
      
      if ( (/\.(png|jpeg|jpg|gif)$/i).test(file.name) ) {
        readImage( file ); 
      } else {
        errors += file.name +" Unsupported Image extension\n";  
      }
      
    }
  }

  // Handle errors
  if (errors) {
    alert(errors); 
  }

});

function removePhoto(){
	while (elPreview.firstChild) {
  	  elPreview.removeChild(elPreview.firstChild);
  	}
}