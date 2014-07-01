function notify(){
	var p = new org.openntf.bstemplate.Pushover();
	
	var profileId = @DbLookup(database.getFilePath(),"profiles","profile", "Form", "[RETURNDOCUMENTUNIQUEID]");
	var profile:NotesDocument = database.getDocumentByUNID(profileId);
	try {
		p.setUserToken(profile.getItemValueString("profileUsertoken"));
		p.setAppToken(profile.getItemValueString("profileApptoken"));	
		
		p.setMessage(sessionScope.message);
		p.setUrl(sessionScope.url);
		p.setUrlTitle(sessionScope.url_title);
		p.setDevice(sessionScope.device);
		
		p.send();
		
		var res = p.getResponse();
		viewScope.put("response", res.getMessage());
		viewScope.put("status", res.getStatus());
		viewScope.put("error", res.getError());
	} catch(e) {
		
	}
}