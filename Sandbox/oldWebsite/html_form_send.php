<?php
if(isset($_POST['email'])) {
     
    $email_to = "Hardik.R.Bhatt@gmail.com";
     
    $email_subject = "Message from Website";
     
     
    function died($error) {
        // your error code can go here
        echo "I are very sorry, but there were error(s) found with the form you submitted. ";
        echo "These errors appear below.<br /><br />";
        echo $error."<br /><br />";
        echo "Please go back and fix these errors.<br /><br />";
        die();
    }
     
    // validation expected data exists
    if(!isset($_POST['first_name']) ||
        !isset($_POST['last_name']) ||
        !isset($_POST['email']) ||
        !isset($_POST['website']) ||
        !isset($_POST['message'])) {
        died('I are sorry, but there appears to be a problem with the form you submitted.');       
    }
     
    $first_name = $_POST['first_name']; // required
    $last_name = $_POST['last_name']; // required
    $email_from = $_POST['email']; // required
    $website = $_POST['website']; // not required
    $message = $_POST['message']; // required
     
    $error_message = "";
    $email_exp = '/^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$/';
  if(!preg_match($email_exp,$email_from)) {
    $error_message .= 'The Email Address you entered does not appear to be valid.<br />';
  }
    $string_exp = "/^[A-Za-z .'-]+$/";
  if(!preg_match($string_exp,$first_name)) {
    $error_message .= 'The First Name you entered does not appear to be valid.<br />';
  }
  if(!preg_match($string_exp,$last_name)) {
    $error_message .= 'The Last Name you entered does not appear to be valid.<br />';
  }
  if(strlen($message) < 2) {
    $error_message .= 'The message you entered do not appear to be valid.<br />';
  }
  if(strlen($error_message) > 0) {
    died($error_message);
  }
    $email_message = "Form details below.\n\n";
     
    function clean_string($string) {
      $bad = array("content-type","bcc:","to:","cc:","href");
      return str_replace($bad,"",$string);
    }
     
    $email_message .= "First Name: ".clean_string($first_name)."\n";
    $email_message .= "Last Name: ".clean_string($last_name)."\n";
    $email_message .= "Email: ".clean_string($email_from)."\n";
    $email_message .= "Website: ".clean_string($website)."\n";
    $email_message .= "Message: ".clean_string($message)."\n";
     
     
// create email headers
$headers = 'From: '.$email_from."\r\n".
'Reply-To: '.$email_from."\r\n" .
'X-Mailer: PHP/' . phpversion();
@mail($email_to, $email_subject, $email_message, $headers);  
?>
 
<!-- place your own success html below -->

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Hardi Bhatt's Website | Code</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/coin-slider.css" />
<link rel="stylesheet" type="text/css" href="shadowbox/shadowbox.css">
<script type="text/javascript" src="shadowbox/shadowbox.js"></script>
<script type="text/javascript">
Shadowbox.init({
    handleOversize: "drag",
    modal: true
});

</script>
<script type="text/javascript" src="js/cufon-yui.js"></script>
<script type="text/javascript" src="js/droid_sans_400-droid_sans_700.font.js"></script>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/script.js"></script>
<script type="text/javascript" src="js/coin-slider.min.js"></script>
</head>
<body>
<div class="main">
  <div class="header">
    <div class="header_resize">
      <div class="menu_nav">
         <ul>
          <li><a href="index.html"><span>Home Page</span></a></li>
          <li><a href="myArchive.html"><span>My Work</span></a></li>
          <li><a href="myResume.html"><span>My Resume</span></a></li>
          <li><a href="aboutMe.html"><span>About Me</span></a></li>
          <li class="active"><a href="myContact.html"><span>Contact Me</span></a></li>
        </ul>
      </div>
      <div class="logo">
        <h1><a href="index.html"><span>Hardi Bhatt</span> <small>A Creative Programmer</small></a></h1>
      </div>
      <div class="clr"></div>
      <div class="slider">
        <div id="coin-slider"> <a href="#"><img src="images/slide1.jpg" width="1500" height="327" alt="" /> </a> <a href="#"><img src="images/slide2.jpg" width="1500" height="327" alt="" /> </a> <a href="#"><img src="images/slide3.jpg" width="1500" height="327" alt="" /> </a> </div>
        <div class="clr"></div>
      </div>
      <div class="clr"></div>
    </div>
  </div>
  <div class="content">
    <div class="content_resize">
      <div class="mainbar">
        <div class="article">
          <h2><span>Thank you for contacting me. </span></h2>
          <div class="clr"></div>
          <p><strong>I will be in touch with you very soon.</strong></p>
          <p></p>
        </div>
      </div>
      <div class="sidebar">
        <div class="clr"></div>
        <div class="gadget">
          <h2 class="star"><span>Sidebar</span> Menu</h2>
          <div class="clr"></div>
          <ul class="sb_menu">
            <li><a href="myResume.html">Resume</a></li>
            <li><a href="gameSamples.html">Game Samples</a></li>
            <li><a href="codeSamples.html">Code Samples</a></li>
            <li><a href="digitalSamples.html">Digital Samples</a></li>
            <li><a href="myHobbies.html">Hobbies</a></li> 
			<li><a href="myTech.html">Resources</a></li>
            <li><a href="myWebsites.html">Favorite Websites</a></li>
          </ul>
        </div>
      </div>
      <div class="clr"></div>
    </div>
  </div>
  <div class="fbg">
    <div class="fbg_resize">
       <div class="col c1">
        <h2><span></span> 	</h2>
        <p></p>    
	  </div>      
      <div class="col c2">
        <h2><span>Contact</span> Me</h2>
        <p>For information regarding my portfolio or even if you just want to say hello.</p>
        <p class="contact_info"> <span>Telephone:</span> +See Resume<br />
		  <span>E-mail:</span><a href="mailto:Hardik.R.Bhatt@gmail.com">Hardik.R.Bhatt@gmail.com</a></p>
      </div>
	  <div class="col c3">
        <h2><span></span> 	</h2>
        <p></p>        
      </div>
      <div class="clr"></div>
    </div>
  </div>
   <div class="footer">
    <div class="footer_resize">
      <p class="lf">&copy; Copyright <a href="http://www.hardibhatt.com/">http://www.hardibhatt.com/</a> 2011.</p>
      <div style="clear:both;"></div>
    </div>
  </div>
</div>
</body>
</html>

 
<?php
}
die();
?>