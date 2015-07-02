<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>
    <head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <title>Insert title here</title>
	    <script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
	    <script src="js/jquery.growl.js" type="text/javascript"></script>
		<link href="css/jquery.growl.css" rel="stylesheet" type="text/css" />
	    
	    <script type="text/javascript">


	    function showNotify(msg){
	    	$.growl.notice({ message: msg });
		}

		function generateFile(){
			 $.ajax({
		            url: 'generatefile',
		            type: 'GET',
		            success: function(data){
			            return;
		            }
		        });
		}

		function register(){
	    	$.get("subscribe?register=true", function( data ) {
	    		  console.log("terminou o processo! " + data);
	    		  if (data != undefined ){
	    		  	showNotify("Arquivo gerado com sucesso " +data);
	    		  }
	    		  register();
	    	  });
		}
	    
	    $(document).ready(function(){

	    	register();
	    	
	        $("#btn_enviar").click(function(event) {
	            event.preventDefault();
	            event.stopPropagation();

	            generateFile();
	            
	    	});
	    	
	    });
    	</script>
    </head>
	    <body>
		    <form>
				Gerar Arquivo:<br />
		    	<input type="button" id="btn_enviar" value="Gerar" />
		    </form>
	    </body>
    </html>