//Setup the internal for the threadwatch execution
window.setInterval(threadWatch, 10000);

//Starting up the storage area
var host = location.hostname || (window.opener ? window.opener.location.hostname : false) ||  null;
if ( typeof(globalStorage) != 'undefined' && typeof(localStorage) == 'undefined' && host !== null ){
	var localStorage = globalStorage[host];
}


//Dependencies for thread watching are YUI JSON, Connect, others?
                function threadWatch(){
                        if (null == localStorage.getItem("watching")){
                                return;
                        }else{
				toggleLink();
                                var watching = YAHOO.lang.JSON.parse(localStorage.getItem("watching"));
                                //each THREAD id is a property of the object.
                                for(property in watching){
                                        //If you disable this gig, you'll see that the property is the thread id
                                        //AND the value is the id of the most recently seen post..
                                        //alert(property + ":" + watching[property]);
                                        if (typeof(thisThreadId) != 'undefined' && property == thisThreadId){
                                                //update THIS thread.. we're watching it, we know it will get updated
                                                watchThisThread();
						//refresh watching but dont modify it
						var newWatching = YAHOO.lang.JSON.parse(localStorage.getItem("watching"));
						updatePage(newWatching[property], property);
						//this will update the link to the page we're currently on to the most recent thread
                                        }else{
                                                //lets go see if there is an update to this thread
                                                //What is the current post id of the thread we're trying to check the status of?
                                                var postIdForThisThread = watching[property];
                                                var callback = {
                                                        success: function(response){
                	                                        var result = YAHOO.lang.JSON.parse(response.responseText);
                        	                                if (result != undefined && result.RemotePost.identifier != -1){
									var threadIdWeGot = result.RemotePost.parentIdentifier;
                                	                                var postIdWeGot = result.RemotePost.identifier;
                                        	                        if (postIdWeGot != -1 && postIdWeGot != watching[threadIdWeGot]){
										updatePage(watching[threadIdWeGot],threadIdWeGot,true);
                                                        	        }
                                                      		}else if (result != undefined && result.RemotePost.identifier == -1 && result.RemotePost.parentIdentifer != -1){
									//we should delete it.. right? 
									var threadIdWeGot = result.RemotePost.parentIdentifier;
									updatePage(-1,threadIdWeGot);
								}
	                                                },
	                                                failure: function(response){
	                                                         //alert(response);
	                                                }
                                                };
                                                var transaction = YAHOO.util.Connect.asyncRequest('GET', '/remote/rest/post/next/' + postIdForThisThread + '/?watch=true', callback, null);
                                        }
                                }
                        }
                }
		//end threadwatch

		function updatePage(postId, threadId, alarm){
                        var innerPanel = document.getElementById("watchPanelBody");
			if (innerPanel != undefined){
				var alarmNow = false;				
				var theDiv = document.getElementById("watch"+threadId);
				if (postId == -1){
					innerPanel.removeChild(theDiv);
				}else{
		                        if (theDiv == undefined || theDiv == null){
			                        theDiv = document.createElement("div");
		                                theDiv.id = "watch"+threadId;
		                                innerPanel.appendChild(theDiv);
						//only alarm when the div first appears
						alarmNow = true;
		                        }
		                        //set the content
		                        theDiv.innerHTML = threadId + "<span class=\"watchLink\">[<a href=\"/chan/thread/" + threadId + "#" + postId + "\">Open</a>]&nbsp;[<a href=\"javascript:unwatchThread('"+threadId+"');\">Unwatch</a>]</span>";
		                        //NOT updating the watching thing.. since the user hasn't gone to it yet.. that screen being open will do it
				}
				if (alarm == true && alarmNow){
					var attributes = {
						backgroundColor: { to: '#38546A' }
					};
					var anim = new YAHOO.util.ColorAnim(theDiv, attributes);
					anim.onComplete.subscribe(function(){
						var attributes1 = {
                	                                backgroundColor: { to: '#F2F2F2' }
        	                                };
                        	                var anim2 = new YAHOO.util.ColorAnim(theDiv, attributes1);
						anim2.animate();
					});
 					anim.animate();
				}
			}
		}
		//end updatePageFunction 
		
		//method to add the current thread
		//Depends on the currentPostId being set
		//this just updates the current threads status to be what we just saw 
		function watchThisThread(){
			if ((typeof(currentPostId) != 'undefined') && (typeof(thisThreadId) != 'undefined')){
	                        var watching;
				//grabbing the current settings
	                        if (localStorage.getItem("watching") == null){
	                                watching = new Object();
	                        }else{
	                                watching = YAHOO.lang.JSON.parse(localStorage.getItem("watching"));
	                        }
	                        //We are going to set the most recent post id to this watching threads last-viewed-post-id.
	                        watching[thisThreadId] = currentPostId;
	                        localStorage.setItem("watching",YAHOO.lang.JSON.stringify(watching));
			}else{
				alert("You shouldn't be calling this method... only for the thread view. Developer issue.");
			}
                }
		//end watchThisThread
		
		function toggleWatch(){
			if ((typeof(currentPostId) != 'undefined') && (typeof(thisThreadId) != 'undefined')){
                                var watching;
                                //grabbing the current settings
                                if (localStorage.getItem("watching") == null){
                                        watching = new Object();
                                }else{
                                        watching = YAHOO.lang.JSON.parse(localStorage.getItem("watching"));
                                }
                                if (!isThreadWatched()){
                                        //We are going to set the most recent post id to this watching threads last-viewed-post-id.
                                        watching[thisThreadId] = currentPostId;
                                }else{
                                        delete watching[thisThreadId];
                                }
                                localStorage.setItem("watching",YAHOO.lang.JSON.stringify(watching));
                                //Should we FORCE a refresh of the panel that will list the watched threads and their status?
                                toggleLink();
                        }else{
                                alert("You shouldn't be calling this method... only for the thread view. Developer issue.");
                        }
		}

		function isThreadWatched(){
			if ((typeof(currentPostId) != 'undefined') && (typeof(thisThreadId) != 'undefined')){
                                var watching;
                                if (localStorage.getItem("watching") == null){
                                        watching = new Object();
                                }else{
                                        watching = YAHOO.lang.JSON.parse(localStorage.getItem("watching"));
                                }
				if (typeof(watching[thisThreadId]) != 'undefined'){
					return true;
				}else{
					return false;
				}
                        }else{
                                alert("You shouldn't be calling this method... only for the thread view. Developer issue.");
                        }
		}

		//toggle link
		function toggleLink(){
	                var watchLink = document.getElementById("watchLink");
			if (watchLink != undefined && watchLink != null){
		                if (isThreadWatched()){
	       	 	                watchLink.innerHTML = "Unwatch";
       	        		}else{
					watchLink.innerHTML = "Watch";
				}
			}
		}

		function setupWatchPanel(){
			// Instantiate a panel for navbar
                        var watchPanel = new YAHOO.widget.Panel("watchPanel", { 
				width:"30em", visible:true, constraintoviewport:true, modal:false} );
                        watchPanel.setHeader("Thread Watching");
                        watchPanel.setFooter("");
                        watchPanel.render();
		}

		function unwatchThread(threadId){
			var watching = YAHOO.lang.JSON.parse(localStorage.getItem("watching"));
                        delete watching[threadId];
                        localStorage.setItem("watching",YAHOO.lang.JSON.stringify(watching));
			document.getElementById("watchPanelBody").removeChild(document.getElementById("watch"+threadId));
		}

////////starting execution!!!!
	if (typeof(localStorage) != 'undefined'){
		//Enable the watch link
		var watchLink = document.getElementById("watchLink");
		var watchSpan = document.getElementById("watchSpan");

		//decide if the thing is being watched or not
		if ((watchSpan != null) && (watchLink != null)){
			toggleLink();
			watchSpan.style.display = "";		
		}
	}
