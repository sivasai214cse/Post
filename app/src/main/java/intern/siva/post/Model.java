package intern.siva.post;

public class Model {
    private String name;
    private String image;
    private String likes;
    private String id;
    private String date;
    String comments;



    public Model(String name, String image, String likes, String id, String date,String comments) {
        this.name = name;
        this.image = image;
        this.likes = likes;
        this.id = id;
        this.date = date;
        this.comments=comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}

