package model;


public class People {

    String url;
    String name;
    String text;


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
        return "People{" +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                "url='" + url + '\'' +
                '}';
    }
}
