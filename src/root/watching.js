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
                                var watching = YAHOO.lang.JSON.parse(localStorage.getItem("watching"));
                                //each THREAD id is a property of the object.
                                for(property in watching){
                                        //If you disable this gig, you'll see that the property is the thread id
                                        //AND the value is the id of the most recently seen post..
                                        //alert(property + ":" + watching[property]);
                                        if (typeof(thisThreadId) != 'undefined' && property == thisThreadId){
                                                //update THIS thread.. we're watching it, we know it will get updated
                                                watchThisThread();
                                        }else{
                                                //lets go see if there is an update to this thread
                                                //What is the current post id of the thread we're trying to check the status of?
                                                var postIdForThisThread = watching[property];
                                                var callback = {
                                                        success: function(response){
                                                        var result = YAHOO.lang.JSON.parse(response.responseText);
                                                        if (result != undefined && result.RemotePost.identifier != -1){
                                                                var postIdWeGot = result.RemotePost.identifier;
                                                                if (postIdWeGot != -1 && postIdWeGot != postIdForThisThread){
                                                                        //FIRE the update!!! something we are watching got updated!!!
                                                                //     alert(property + " was updated");
                                                                        //and then update the cookie
                                                                        watching[property] = postIdWeGot;
                                                                        localStorage.setItem("watching",YAHOO.lang.JSON.stringify(watching));
                                                                }
                                                        }
                                                },
                                                        failure: function(response){
                                                                //alert(response);
                                                        }
                                                };
                                                var transaction = YAHOO.util.Connect.asyncRequest('GET', '/remote/rest/post/next/' + postIdForThisThread + '/', callback, null);
                                        }
                                }
                        }
                }
		//end threadwatch
		
		//method to add the current thread
		//Depends on the currentPostId being set
		function watchThisThread(modifyState){
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
				if(modifyState){
		                        localStorage.setItem("watching",YAHOO.lang.JSON.stringify(watching));
				}
	                        //Should we FORCE a refresh of the panel that will list the watched threads and their status?
				toggleLink();
			}else{
				alert("You shouldn't be calling this method... only for the thread view. Developer issue.");
			}
                }
		//end watchThisThread

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
	                if (isThreadWatched()){
        	                watchLink.innerHTML = "Unwatch";
               		}else{
				watchLink.innerHTML = "Watch";
			}
		}

////////starting execution!!!!
	if (typeof(localStorage) != 'undefined'){
		//Enable the watch link
		var watchLink = document.getElementById("watchLink");
		var watchSpan = document.getElementById("watchSpan");

		//decide if the thing is being watched or not
		toggleLink();

		watchSpan.style.display = "";		
	}
