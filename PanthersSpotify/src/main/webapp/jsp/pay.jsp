<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  <script src="${cp}/resources/js/payment.js" /></script>
<div class="suggestion-container" id = "discover-container">

	<div style="text-align:center;">
		<h2 id="upgradeAccountMessage"></h2>
	</div>
  <div class="panel-group" style= "margin-top: 5%;">
     <div class="panel panel-default">
       <div class="panel-heading">
         <div class="form-group row">
           <label for="staticEmail" class="col-md-4 col-form-label">Accpeted Credit Card Company:</label>
           <div class="col-md-offset-2 col-md-6">
             <i class="fa fa-cc-mastercard" style="font-size:3em"></i>
             <i class="fa fa-cc-visa" style="font-size:3em"></i>
             <i class="fa fa-cc-discover" style="font-size:3em;"></i>
           </div>
         </div>
       </div>
         <div class="panel-body">
           <form action="javascript:submitUpgradeForm()">
             <div class="form-group row">
               <label for="staticEmail" class="col-md-4 col-form-label">Your Name:</label>
               <div class="col-md-8">
                 <input type="text" class="form-control" id="holderName" placeholder="enter your name"required>
               </div>
             </div>
             <div class="form-group row">
               <label for="inputPassword" class="col-md-4 col-form-label">Your Email Address:</label>
               <div class="col-md-8">
                 <input type="email" class="form-control"  placeholder="enter your email address" required>
               </div>
             </div>
             <div class="form-group row">
               <label for="inputPassword" class="col-md-4 col-form-label">Amount($):</label>
               <div class="col-md-8">
                 <input type="text" class="form-control"  placeholder="$10/month" required>
               </div>
             </div>
             <div class="form-group row">
               <label for="inputPassword" class="col-md-4 col-form-label">Card Number:</label>
               <div class="col-md-8">
                 <input type="text" class="form-control" name ="cardNum" id="cardNumber" placeholder="enter valid card number" required>
               </div>
             </div>
             <div class="form-group row">
               <label for="inputPassword" class="col-md-8 col-form-label">Expiration Date:</label>
                <label for="inputPassword" class="col-md-4 col-form-label">CV Code:</label>
               <div class="col-md-8">
                 <input type="text" class="form-control" id="expirationDate" placeholder="MM/YY" required>
               </div>
               <div class="col-md-4">
                 <input type="text" class="form-control"  id="cvv" placeholder="CVV" required>
               </div>
             </div>
             <div class="form-group row">
               <div class="col-md-offset-8 col-md-4">
                 <input type="submit" class="form-control"  value="Upgrade">
               </div>

             </div>
           </form> <!-- end of form -->
         </div> <!-- end of panel body -->
       </div>
     </div> <!-- end of panel-group -->

</div>