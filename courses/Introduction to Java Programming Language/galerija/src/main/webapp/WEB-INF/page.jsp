<!DOCTYPE html>
<html>
	<head>
		<!-- Implementacija dinamičkog dohvata informacija kako korisnik utipkava filter... -->
		<meta charset="UTF-8">
		<title>Canada</title>
		
		<style>

			h2{
			font:small-caps;
			color: red;
			position: relative;
			left: 45%;
			}
			
			h2:HOVER{
				color: #EE5522;
				
			}
			
			.btnContainer {
			
				display: table;
				position: relative;
				width: 50%;	
				left: 25%;
				right: 25%;		
				max-height: 20%;
			}
			
			button {
	             background-color: #EE5522;
	             font: bold;
	             
	             max-height: 30px;
	             max-width: 50px;
				 height: inherit;
	             width: inherit;
	             padding: 30px;
	             position: relative;
	             top: 0;
	             left: 0;
	             
	       }
	          button:HOVER {
				background:red;
	    	}
	    	
	    	.texts {
	    		font: italic;
	    		max-width: inherit;
	    		position: absolute;
	    		left: 5%;
	    		top: 37%;
	    		width: inherit;
	    		font-size: 90%;
	    	}
	    	
	    	img {
				position: relative;
				top 20;
				left 20;
				
				
			}
		
			h3 {
				font:small-caps;
				color: red;
			}
		
			h3:HOVER {
				color: #EE5522;
			}
		</style>
		
		<script type="text/javascript"><!--
		  
		  function showRealImage(realPath){
			  		
			
				
			var xmlhttp;
			
			  if (window.XMLHttpRequest) {
					// code for IE7+, Firefox, Chrome, Opera, Safari
					xmlhttp=new XMLHttpRequest();
				} else {
					// code for IE6, IE5
					xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
				}
				
				xmlhttp.onreadystatechange = function() {
					if(xmlhttp.readyState==4 && xmlhttp.status==200) {
							
						var element = document.getElementById("bigPicture");
						if(element != null){
							element.parentNode.removeChild(element);
						}

						
						
						var text = xmlhttp.responseText;
						var elems = text.split("#");


						var div = document.createElement("DIV");

						div.setAttribute("id", "bigPicture");


						var newImg = document.createElement("img");	
						newImg.setAttribute("src", "servlets/pictureProvider?path=" + realPath + "&dummy=" + Math.random(), true);
						
						div.appendChild(newImg);

						var br = document.createElement("br");
						div.appendChild(br);
						var desc = document.createElement("txt");
						desc.innerHTML = "Description: " + elems[0];
						desc.setAttribute("id", "textOne");
						div.appendChild(desc);
						var br = document.createElement("br");
						div.appendChild(br);
						var desc = document.createElement("txt");
						desc.innerHTML = "Tags: " + elems[1];
						desc.setAttribute("id", "textTwo");
						div.appendChild(desc);
						
						document.body.appendChild(div);
							  
						}
					}
				xmlhttp.open("GET", "servlets/pictureDescription?path=" + realPath + "&dummy=" + Math.random(), true);
				xmlhttp.send();	
		  }
				
			
			
		 
		  function importThumbnails(tag){

			var img = document.getElementsByTagName("img");
			while(img.length > 0){
				img[0].parentNode.removeChild(img[0]);	
			}
			
			  var xmlhttp;
				
			  if (window.XMLHttpRequest) {
					// code for IE7+, Firefox, Chrome, Opera, Safari
					xmlhttp=new XMLHttpRequest();
				} else {
					// code for IE6, IE5
					xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
				}
				
				xmlhttp.onreadystatechange = function() {
					if(xmlhttp.readyState==4 && xmlhttp.status==200) {
							
						var element = document.getElementById("bigPicture");
						if(element != null){
							element.parentNode.removeChild(element);
						}

						
						var text = xmlhttp.responseText;
						var elems = text.split("#");
						for(var i = 0; i < elems.length; i++){
							var img=document.createElement("img");
							  img.setAttribute("src", "servlets/thumbnails?tag=" + elems[i] + "&dummy=" + Math.random(), true);
							  img.setAttribute("alt", tag + " image");
						
							img.setAttribute("id", elems[i]);
							img.setAttribute("onclick", "showRealImage(this.id)");
							
							
							
							  document.body.appendChild(img);
							  
						}
					}
				}
				xmlhttp.open("GET", "servlets/tagProvider?tag=" + tag + "&dummy=" + Math.random(), true);
				xmlhttp.send();
		  }
		
		  
		  function getTags() {

			var xmlhttp;
			
			if (window.XMLHttpRequest) {
				// code for IE7+, Firefox, Chrome, Opera, Safari
				xmlhttp=new XMLHttpRequest();
			} else {
				// code for IE6, IE5
				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			xmlhttp.onreadystatechange = function() {
				if(xmlhttp.readyState==4 && xmlhttp.status==200) {
					var text = xmlhttp.responseText;
					var elems = text.split("#");
					
					var div = document.createElement("div");
					div.setAttribute("class", "btnContainer");
					
					for(var i = 0; i < elems.length; i++){
						var btn = document.createElement("BUTTON");
						btn.setAttribute("class", "btn");
					    var t = document.createElement("txt");
					    t.innerHTML = elems[i];
					    t.setAttribute("class", "texts");
					    btn.appendChild(t);
					    btn.setAttribute("id", elems[i]);
					    btn.setAttribute("onclick", "importThumbnails(this.id)");
					    
					    div.appendChild(btn);
					}
					
				    document.body.appendChild(div);

				}
			}
			xmlhttp.open("GET", "servlets/tags?" + "dummy=" + Math.random(), true);
			xmlhttp.send();
		  }
		//--></script>
	</head>
	<body id="container" onload="getTags()">
	<h2>Canada &lt;3</h2>		
		<h3>Tags: </h3>


	</body>
	
</html>