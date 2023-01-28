package base;

public class Person {
	private int id;
	private String name;
	private String givenName = "Không rõ";
	private String father = "Không rõ";
	private String dateOfBirth;
	private String dateOfDeath;
	private String desc;
	private String dynastyName;
	
	
	public Person(int id, String name, String givenName, String father, String dateOfBirth, String dateOfDeath,
			String desc, String dynastyName) {
		this.id = id;
		this.name = name;
		this.givenName = givenName;
		this.father = father;
		this.dateOfBirth = dateOfBirth;
		this.dateOfDeath = dateOfDeath;
		this.desc = desc;
		this.dynastyName = dynastyName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getFather() {
		return father;
	}
	public void setFather(String father) {
		this.father = father;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getDateOfDeath() {
		return dateOfDeath;
	}
	public void setDateOfDeath(String dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDynastyName() {
		return dynastyName;
	}
	public void setDynastyName(String dynastyName) {
		this.dynastyName = dynastyName;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Person) {
			Person p = (Person) obj;
			return this.getName().equalsIgnoreCase(p.getName());
		}
		return false;
	}
}
