package intern.siva.post;

public class Model2 {
    String body;
    String user;
    String time;
    String id;

    public Model2(String body, String user, String time, String id) {
        this.body = body;
        this.user = user;
        this.time = time;
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
