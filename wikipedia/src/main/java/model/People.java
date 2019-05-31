package model;

public class People {

	String url;
	String name;
	String text;
	String id;
	int searched = 1;

	public void incrementSearch() {
		this.searched = this.searched + 1;
	}

	public int getSearched() {
		return searched;
	}

	public void setSearched(int searched) {
		this.searched = searched;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "People{" + ", name='" + name + '\'' + ", text='" + text + '\'' + "url='" + url + '\'' + '}';
	}
}
