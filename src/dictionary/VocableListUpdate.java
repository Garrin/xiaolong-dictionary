package dictionary;

public class VocableListUpdate {
	
	public static final String CHANGE_VOCABLE_UPDATE_TYPE = "change";
	public static final String DELETE_VOCABLE_UPDATE_TYPE = "delete";
	public static final String ADD_VOCABLE_UPDATE_TYPE = "add";
	
	public Vocable beforeChangeVocable;
	public Vocable afterchangeVocable;
	
	public Vocable deletedVocable;
	
	public Vocable addedVocable;
	
	public String typeOfUpdate = null; 
	
	public VocableListUpdate(Vocable beforeChangeVocable, Vocable afterChangeVocable) {
		this.typeOfUpdate = VocableListUpdate.CHANGE_VOCABLE_UPDATE_TYPE;
	}
	
	public VocableListUpdate(Vocable vocable, String typeOfUpdate) {
		this.typeOfUpdate = typeOfUpdate;
	}
}
