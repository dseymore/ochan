/*
 * Ext JS Library 2.0 Beta 1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

Ext.data.Connection=function(A){Ext.apply(this,A);this.addEvents({"beforerequest":true,"requestcomplete":true,"requestexception":true});Ext.data.Connection.superclass.constructor.call(this)};Ext.extend(Ext.data.Connection,Ext.util.Observable,{timeout:30000,autoAbort:false,disableCaching:true,request:function(E){if(this.fireEvent("beforerequest",this,E)!==false){var C=E.params;if(typeof C=="function"){C=C.call(E.scope||window,E)}if(typeof C=="object"){C=Ext.urlEncode(E.params)}if(this.extraParams){var G=Ext.urlEncode(this.extraParams);C=C?(C+"&"+G):G}var B=E.url||this.url;if(typeof B=="function"){B=B.call(E.scope||window,E)}if(E.form){var D=Ext.getDom(E.form);B=B||D.action;var I=D.getAttribute("enctype");if(E.isUpload||(I&&I.toLowerCase()=="multipart/form-data")){return this.doFormUpload(E,C,B)}var H=Ext.lib.Ajax.serializeForm(D);C=C?(C+"&"+H):H}var J=E.headers;if(this.defaultHeaders){J=Ext.apply(J||{},this.defaultHeaders);if(!E.headers){E.headers=J}}var F={success:this.handleResponse,failure:this.handleFailure,scope:this,argument:{options:E},timeout:this.timeout};var A=E.method||this.method||(C?"POST":"GET");if(A=="GET"&&(this.disableCaching&&E.disableCaching!==false)||E.disableCaching===true){B+=(B.indexOf("?")!=-1?"&":"?")+"_dc="+(new Date().getTime())}if(typeof E.autoAbort=="boolean"){if(E.autoAbort){this.abort()}}else{if(this.autoAbort!==false){this.abort()}}if((A=="GET"&&C)||E.xmlData){B+=(B.indexOf("?")!=-1?"&":"?")+C;C=""}this.transId=Ext.lib.Ajax.request(A,B,F,C,E);return this.transId}else{Ext.callback(E.callback,E.scope,[E,null,null]);return null}},isLoading:function(A){if(A){return Ext.lib.Ajax.isCallInProgress(A)}else{return this.transId?true:false}},abort:function(A){if(A||this.isLoading()){Ext.lib.Ajax.abort(A||this.transId)}},handleResponse:function(A){this.transId=false;var B=A.argument.options;A.argument=B?B.argument:null;this.fireEvent("requestcomplete",this,A,B);Ext.callback(B.success,B.scope,[A,B]);Ext.callback(B.callback,B.scope,[B,true,A])},handleFailure:function(A,C){this.transId=false;var B=A.argument.options;A.argument=B?B.argument:null;this.fireEvent("requestexception",this,A,B,C);Ext.callback(B.failure,B.scope,[A,B]);Ext.callback(B.callback,B.scope,[B,false,A])},doFormUpload:function(E,A,B){var C=Ext.id();var F=document.createElement("iframe");F.id=C;F.name=C;F.className="x-hidden";if(Ext.isIE){F.src=Ext.SSL_SECURE_URL}document.body.appendChild(F);if(Ext.isIE){document.frames[C].name=C}var D=Ext.getDom(E.form);D.target=C;D.method="POST";D.enctype=D.encoding="multipart/form-data";if(B){D.action=B}var L,J;if(A){L=[];A=Ext.urlDecode(A,false);for(var H in A){if(A.hasOwnProperty(H)){J=document.createElement("input");J.type="hidden";J.name=H;J.value=A[H];D.appendChild(J);L.push(J)}}}function G(){var M={responseText:"",responseXML:null};M.argument=E?E.argument:null;try{var O;if(Ext.isIE){O=F.contentWindow.document}else{O=(F.contentDocument||window.frames[C].document)}if(O&&O.body){M.responseText=O.body.innerHTML}if(O&&O.XMLDocument){M.responseXML=O.XMLDocument}else{M.responseXML=O}}catch(N){}Ext.EventManager.removeListener(F,"load",G,this);this.fireEvent("requestcomplete",this,M,E);Ext.callback(E.success,E.scope,[M,E]);Ext.callback(E.callback,E.scope,[E,true,M]);setTimeout(function(){Ext.removeNode(F)},100)}Ext.EventManager.on(F,"load",G,this);D.submit();if(L){for(var I=0,K=L.length;I<K;I++){Ext.removeNode(L[I])}}}});Ext.Ajax=new Ext.data.Connection({autoAbort:false,serializeForm:function(A){return Ext.lib.Ajax.serializeForm(A)}});