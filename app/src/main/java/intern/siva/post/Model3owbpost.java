package intern.siva.post;

public class Model3owbpost {
    String date;
    String details;
    String id;
    String image;
    String like;

    public Model3owbpost(String date, String details, String id, String image, String like) {
        this.date = date;
        this.details = details;
        this.id = id;
        this.image = image;
        this.like = like;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }
}
