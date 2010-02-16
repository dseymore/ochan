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
                                                      		}else if (result != undefined && result.RemotePost.identifier == -1 && result.RemotePost.parentIdentifier != -1){
									//we should delete it.. right? 
									var threadIdWeGot = result.RemotePost.parentIdentifier;
									updatePage(-1,threadIdWeGot);
								}else if (result != undefined && result.RemotePost.identifier == -1 && result.RemotePost.parentIdentifier == -1 ){
									deleteWatch(this.threadId);
									//we need to unwatch it.
								}else{	
									//worse case situation
								}
	                                                },
	                                                failure: function(response){
	                                                         //alert("failure: " + response);
	                                                }
                                                };
						callback.threadId = property;
                                                var transaction = YAHOO.util.Connect.asyncRequest('GET', '/remote/rest/post/next/' + postIdForThisThread + '/?watch=true', callback, null);
                                        }
                                }
                        }
                }
		//end threadwatch

		//we call this when want to remove a thread that was deleted..
		function deleteWatch(thisThreadId){
                        var watching = YAHOO.lang.JSON.parse(localStorage.getItem("watching"));
                        var names = YAHOO.lang.JSON.parse(localStorage.getItem("watchingNames"));
			//we should GROWL that the thread was deleted.
			$.jGrowl('Thread \"' + names[thisThreadId] + '\" was deleted.', { life: 5000 });

                        delete watching[thisThreadId];
                        delete names[thisThreadId];
                        localStorage.setItem("watching",YAHOO.lang.JSON.stringify(watching));
                        localStorage.setItem("watchingNames",YAHOO.lang.JSON.stringify(names));			
		}

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
					var names = YAHOO.lang.JSON.parse(localStorage.getItem("watchingNames"));
		                        //set the content
		                        theDiv.innerHTML = names[threadId] + "<span class=\"watchLink\">[<a href=\"/chan/thread/" + threadId + "#" + postId + "\">Open</a>]&nbsp;[<a href=\"javascript:unwatchThread('"+threadId+"');\">Unwatch</a>]</span>";
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
				//we still want to call this as soon as the page renders.. 
				//alert("You shouldn't be calling this method... only for the thread view. Developer issue.");
				return 0;
			}
                }
		//end watchThisThread
		
		function toggleWatch(name){
			if ((typeof(currentPostId) != 'undefined') && (typeof(thisThreadId) != 'undefined')){
                                var watching;
				var names;
                                //grabbing the current settings
                                if (localStorage.getItem("watching") == null){
                                        watching = new Object();
                                }else{
                                        watching = YAHOO.lang.JSON.parse(localStorage.getItem("watching"));
                                }
				if (localStorage.getItem("watchingNames") == null){
					names = new Object();
				}else{
					names = YAHOO.lang.JSON.parse(localStorage.getItem("watchingNames"));
				}
                                if (!isThreadWatched()){
                                        //We are going to set the most recent post id to this watching threads last-viewed-post-id.
                                        watching[thisThreadId] = currentPostId;
					if (name != null && name != undefined){
						names[thisThreadId] = name;
					}else{
						names[thisThreadId] = thisThreadId;
					}
                                }else{
                                        delete watching[thisThreadId];
					delete names[thisThreadId];
                                }
                                localStorage.setItem("watching",YAHOO.lang.JSON.stringify(watching));
				localStorage.setItem("watchingNames",YAHOO.lang.JSON.stringify(names));
                                //Should we FORCE a refresh of the panel that will list the watched threads and their status?
                                toggleLink();
                        }else{
                                alert("You shouldn't be calling this method... only for the thread view. Developer issue.");
                        }
		}

		function watchFunction(){
			if (!isThreadWatched()){
				//boot up the dialog! :D
				dialog1.show();
			}else{
				toggleWatch();
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
                        watchPanel = new YAHOO.widget.Panel("watchPanel", { 
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
		////////////////////////////////START DIALOG
		// Define various event handlers for Dialog
		var handleSubmit = function() {
			this.submit();
		};
		var handleCancel = function() {
			//and nothing to do here as well
			this.cancel();
		};
		var handleSuccess = function(o) {
			//nothing to do here eithe
		};
		var handleFailure = function(o) {
			alert("Submission failed: " + o.status);
		};
		// Remove progressively enhanced content class, just before creating the module
		YAHOO.util.Dom.removeClass("dialog1", "yui-pe-content");

		// Instantiate the Dialog
		var dialog1 = new YAHOO.widget.Dialog("dialog1", 
						{ width : "30em",
						  fixedcenter : true,
						  visible : false, 
						  constraintoviewport : true,
						  modal: true,
						  buttons : [ { text:"Submit", handler:handleSubmit, isDefault:true },
							      { text:"Cancel", handler:handleCancel } ]
						});

		// Validate the entries in the form to require that both first and last name are entered
		dialog1.validate = function() {
			var name = document.getElementById("threadname").value;
			if (name == "" || name.length > 80){
				alert("Please enter a name; you'll want to track the thread. (80 characeter limit)");
				return false;
			} else {
				toggleWatch(name);
				this.hide();
				return false;
			}
		};

		// Wire up the success and failure handlers
		dialog1.callback = { success: handleSuccess, failure: handleFailure };
		// Render the Dialog
		dialog1.render();
		/////////////////////////END DIALOG

		//Enable the watch link
		var watchLink = document.getElementById("watchLink");
		var watchSpan = document.getElementById("watchSpan");

		//decide if the thing is being watched or not
		if ((watchSpan != null) && (watchLink != null)){
			toggleLink();
			watchSpan.style.display = "";		
		}
		//as SOON as the page is viewed.. update our watchitudanality
		watchThisThread();

		var watchPanel = "";
	}
