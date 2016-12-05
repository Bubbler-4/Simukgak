package hanjo.simukgak;

/**
 * Created by lg on 2016-12-01.
 */

import android.graphics.drawable.Drawable;
import android.widget.Button;

public class Review {
    private String job;
    private Drawable profile;
    private String id;
    private String store_name;
    private String food_name;
    private String grade;
    private String comment;
    private String name_like;
    private Button like;
    private String time;
//    private Button push_b;

    public void setJob(String _job) {job=_job;}
    public void setProfile(Drawable _profile){profile = _profile;}
    public void setId(String _id){id=_id;}
    public void setStore_name(String _store_name){store_name=_store_name;}
    public void setFood_name(String _food_name){food_name=_food_name;}
    public void setGrade(String _grade){grade = _grade;}
    public void setComment(String _comment){comment = _comment;}
    public void setName_like(String _name_like){name_like = _name_like;}
    public void setLike(Button _like){like =_like;}
    public void setTime(String _time){time = _time;}
    //    public void setPush_b(Button _push_b){push_b = _push_b;}
    public String getJob(){return this.job;}
    public Drawable getProfile(){return this.profile;}
    public String getId(){return this.id;}
    public String getStore_name(){return this.store_name;}
    public String getFood_name(){return this.food_name;}
    public String getGrade(){return this.grade;}
    public String getComment(){return this.comment;}
    public String getName_like(){return this.name_like;}
    public Button getLike(){return this.like;}
    public String getTime(){return this.time;}
}