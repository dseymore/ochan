window.setInterval(stepImages,3000);

var recentImages = undefined;
var recentImagesSize = 0;
var currentposition = 0;
var counter = 4;
var stop = false;

function openWindow(id){
	window.open("/chan/redirect/"+id,"recent"+id);
}

function stepImages(){
	var temp = recentImages;
	var tempSize = recentImagesSize;
	//if we need data, go get it. 
	if (recentImages == undefined){
		newData();
		currentposition = 0;
	}
	if (temp != undefined && !stop){
		var position = 0;
		for (property in recentImages){
			if (position == currentposition){
				//display THIS image
				var div = document.getElementById("recentImagesDiv");
				var img = document.createElement("img");
				var span = document.createElement("span");
				//img.src = "/chan/raw/"+recentImages[property]+"?recent=true";
				img.src = "/blankThumb.jpg";
				img.width = "1";
				img.height = "160";
				span.id = "recent"+counter;
				//padding-top: 140px; padding-left: 160px; background-image: url(/chan/raw/725?recent=true); background-repeat: no-repeat; background-position: center top;
				span.onclick = Function('openWindow("'+recentImages[property]+'")');
				span.style.margin = "1px";
				span.style.cursor = "pointer";
				span.style.borderStyle = "dashed";
				span.style.borderWidth = "1px";
				span.style.paddingTop = "140px";
				span.style.paddingLeft = "160px"
				span.style.backgroundImage = "url(/chan/raw/"+recentImages[property]+"?recent=true)";
				span.style.backgroundRepeat = "no-repeat";
				span.style.backgroundPosition = "center top";
				span.appendChild(img);
				var oldImg = document.getElementById("recent"+(counter-4));
				if (oldImg != undefined){
					div.removeChild(oldImg);
				}
				div.appendChild(span);
				counter++;
			}
			position++;
		}
		currentposition++;
		//now, if the current position is bigger than the sum.. 
		if (currentposition == recentImagesSize){
			recentImages = undefined;
		}
	}
}

function newData(){
	//step 1.. get the object if we need it. 
	var callback = {
		success: function(response){
                	var result = YAHOO.lang.JSON.parse(response.responseText);
                        if (result != undefined && result.RemoteGroup.a0 != -1){
				recentImages = result.RemoteGroup;
				size = 0;
				for (property in recentImages){
					size++;
				}
				recentImagesSize = size;
			}
                },
                failure: function(response){
	 	               //alert("failure: " + response);
                        }
        };
        var transaction = YAHOO.util.Connect.asyncRequest('GET', '/remote/rest/stats/images/?recent=true', callback, null);	
}
