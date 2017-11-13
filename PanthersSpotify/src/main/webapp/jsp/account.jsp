<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html>
<html lang="en">
  
  <style type="text/css">
  
  body {
    	background-color: #343a40;  
  }
  
  </style>
  
  <body>
  
  <div class="suggestion-container" id = "release-container" style="margin-top: 5%;">
	  <div class="suggestion-container-top">
	    <h3 class="suggestion-topic" style="font-size: 4em;">Accounts:</h3>
	  </div>
	  <div class="line"></div>
	  
	</div>
		<div class="panel-body">
		   <span id='message'></span>
           <form:form action="editUserAccount" method="POST">
             <div class="form-group row">
               <label for="staticEmail" class="col-md-4 col-form-label">Password</label>
               <div class="col-md-8">
                 <input type="text"  class="form-control" id="password" name="password"/>
               </div>
             </div>
             <div class="form-group row">
               <label for="confirmedPassowrd" class="col-md-4 col-form-label">Confirm Password</label>
               <div class="col-md-8">
                 <input type="text" class="form-control" id="confirm_password" name="confirm_password"/>                
               </div>
             </div>          	                                                       
             <div class="form-group row">
               <div class="col-md-4" style="margin-top: 2%;">
                 <input type="submit" class="form-control" value="Submit">
               </div>
             </div>
           </form:form> <!-- end of form -->
         </div> <!-- end of panel body -->
      
        

          
          
        

  </body>
</html>
