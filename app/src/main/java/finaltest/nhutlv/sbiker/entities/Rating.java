package finaltest.nhutlv.sbiker.entities;

/**
 * Created by NhutDu on 07/05/2017.
 */

public class Rating {

    private String _id;
    private String id_history;
    private String rating;
    private String comment;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId_history() {
        return id_history;
    }

    public void setId_history(String id_history) {
        this.id_history = id_history;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
