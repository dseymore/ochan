
function replyhl(id) {
  var tdtags=document.getElementsByTagName("div");
  for (i=0; i<tdtags.length; i++){
    if (tdtags[i].className=="replyhl")
      tdtags[i].className = "reply";
    if (tdtags[i].id == id)
      tdtags[i].className = "replyhl";
  }
}


function init() {
  arr=location.href.split(/#/);
  if (arr[1]) {
  if (arr[1].match(/(q)?([0-9]+)/)) {
    rep=arr[1];
    re=arr[1].replace(/q/,"");
    replyhl(re);
  }
  }
}


onload=init;
