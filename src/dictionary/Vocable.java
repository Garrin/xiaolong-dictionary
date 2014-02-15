
package dictionary;

/**
 * @author XiaoLong
 *
 */
public class Vocable implements Cloneable {
	
	private String topic;
	private String chapter;
	private String firstLanguage;
	private String secondLanguage;
	private String phoneticScript;
	private String learnLevel;
	private String importanceLevel;
	private String description;
	
	public Vocable(String topic, String chapter, String firstLanguage, String secondLanguage, String phoneticScript, String learnLevel){
		this.topic = topic;
		this.chapter = chapter;
		this.firstLanguage = firstLanguage;
		this.secondLanguage = secondLanguage;
		this.phoneticScript = phoneticScript;
		this.learnLevel = learnLevel;
		this.importanceLevel = "";
		this.description = "";
	}
	
	public Vocable(String topic, String chapter, String firstLanguage, String secondLanguage, String phoneticScript, String learnLevel, String importanceLevel, String description){
		this.topic = topic;
		this.chapter = chapter;
		this.firstLanguage = firstLanguage;
		this.secondLanguage = secondLanguage;
		this.phoneticScript = phoneticScript;
		this.learnLevel = learnLevel;
		this.importanceLevel = importanceLevel;
		this.description = description;
	}

	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * @return the chapter
	 */
	public String getChapter() {
		return chapter;
	}

	/**
	 * @param chapter the chapter to set
	 */
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	/**
	 * @return the first language
	 */
	public String getFirstLanguage() {
		return firstLanguage;
	}

	/**
	 * @param first language to set
	 */
	public void setFirstLanguage(String firstLanguage) {
		this.firstLanguage = firstLanguage;
	}

	/**
	 * @return the second language
	 */
	public String getSecondLanguage() {
		return secondLanguage;
	}

	/**
	 * @param secondLanguage the secondLanguage to set
	 */
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}

	/**
	 * @return the phoneticScript
	 */
	public String getPhoneticScript() {
		return phoneticScript;
	}

	/**
	 * @param phoneticScript the phoneticScript to set
	 */
	public void setPhoneticScript(String pinyin) {
		this.phoneticScript = pinyin;
	}

	/**
	 * @return the learnLevel
	 */
	public String getLearnLevel() {
		return learnLevel;
	}

	/**
	 * @param learnLevel the learnLevel to set
	 */
	public void setLearnLevel(String level) {
		this.learnLevel = level;
	}
	
	
	/**
	 * @return the importanceLevel of this vocable
	 */
	public String getImportanceLevel() {
		return this.importanceLevel;
	}
	
	
	/**
	 * @param importanceLevel the importanceLevel to set
	 */
	public void setImportanceLevel(String importanceLevel) {
		this.importanceLevel = importanceLevel;
	}
	
	/**
	 * @return the description of this vocable
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Getter and Setter
	 * @param newFirstLanguageMeaning new first language translation
	 */
	
	public void addGerman(String newFirstLanguageMeaning){
		this.firstLanguage += "/" + newFirstLanguageMeaning;
	}
	
	public void addChinese(String newChineseMeaning){
		this.secondLanguage += "/" + newChineseMeaning;
	}
	
	public void addPinyin(String newPinyin){
		this.phoneticScript += "/" + newPinyin;
	}
	
	public void addTopic(String newTopic){
		this.topic += "/" + newTopic;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}