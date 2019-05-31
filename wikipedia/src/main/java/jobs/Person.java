package jobs;

public class Person {
	@Override
	public String toString() {
		return "Person [uri=" + uri + ", id=" + id + ", Name=" + name + ", searched=" + searched + "]";
	}

	String uri;
	String id;
	String name;
	int searched=1;

	public Person(String id, String uri, String name) {
		this.uri = uri;
		this.id = id;
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getID() {
		return id;
	}

	public void setID(String iD) {
		id = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSearched() {
		return searched;
	}

	public void setSearched(int searched) {
		this.searched = searched;
	}

	public void incrementTimes() {
		searched += 1;
	}

}
