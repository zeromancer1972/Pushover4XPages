function notify(){
	var p = new org.openntf.bstemplate.Pushover();
	
	// set your token here!
	p.setUserToken("u4CcNUVHgBqg1iiqLFeD8AVzY6YVxE");
	// app token here
	p.setAppToken("aEPcYMGyvwbm4VXibPJPJF7YTmFwGj");
	
	
	p.setMessage("Ein neuer Kommentar wurde gepostet");
	try {
		p.send();
	} catch(e){
	}
}