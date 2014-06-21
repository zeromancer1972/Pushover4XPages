function notify(){
	var p = new org.openntf.bstemplate.Pushover();
	
	
	var profileId = @DbLookup(database.getFilePath(),"profiles","profile", "Form", "[RETURNDOCUMENTUNIQUEID]");
	var profile:NotesDocument = database.getDocumentByUNID(profileId);
	try {
		p.setUserToken(profile.getItemValueString("profileUsertoken"));
		p.setAppToken(profile.getItemValueString("profileApptoken"));	
	
		p.setMessage(sessionScope.message);
		p.setUrl(sessionScope.url);
		
		p.send();
	
	} catch(e) {
		
	}
}