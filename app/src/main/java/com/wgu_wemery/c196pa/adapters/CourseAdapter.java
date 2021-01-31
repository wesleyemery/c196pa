package com.wgu_wemery.c196pa.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.wgu_wemery.c196pa.models.Course;
import com.wgu_wemery.c196pa.CourseDetailActivity;
import com.wgu_wemery.c196pa.utils.DatabaseHandler;
import com.wgu_wemery.c196pa.R;

import java.util.ArrayList;

public class CourseAdapter extends ArrayAdapter<Course> {

    private ArrayList<Course> courses;
    private Context mCtx;
    private DatabaseHandler dbHandler;

    public CourseAdapter(ArrayList<Course> courses, Context mCtx, DatabaseHandler dbHandler) {
        super(mCtx, R.layout.term_row,courses);
        this.courses = courses;
        this.mCtx = mCtx;
        this.dbHandler = dbHandler;
    }

    //ViewHolder class
    public static class ItemViewHolder{
        TextView name;
        TextView startDate;
        TextView endDate;
        TextView activeCourse;
        TextView courses;
        ImageView deleteTerm;
        MaterialCardView termCard;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //retrieve position
        Course myCourse = courses.get(position);
        ItemViewHolder itemHolder;
        View output;

        if(convertView==null){

            itemHolder = new ItemViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            convertView = inflater.inflate(R.layout.course_row,parent,false);

            itemHolder.name = convertView.findViewById(R.id.txt_term_name);
            itemHolder.startDate = convertView.findViewById(R.id.txt_term_start);
            itemHolder.endDate = convertView.findViewById(R.id.txt_term_end);
            itemHolder.activeCourse = convertView.findViewById(R.id.txt_term_active);
            itemHolder.deleteTerm = convertView.findViewById(R.id.logo_delete_term);
            itemHolder.termCard = convertView.findViewById(R.id.cardTerm);


            convertView.setTag(itemHolder);
        }else{
            itemHolder = (ItemViewHolder) convertView.getTag();
        }

        itemHolder.name.setText("COURSE NAME: "+myCourse.getCourseTitle());
        itemHolder.startDate.setText("START DATE: "+myCourse.getCourseStartDate());
        itemHolder.endDate.setText("INSTRUCTOR: "+myCourse.getCourseMentor());
        itemHolder.activeCourse.setText("STATUS: "+myCourse.getStatus());
        itemHolder.termCard.setOnClickListener(v->{
            Intent switchIntent = new Intent(mCtx, CourseDetailActivity.class);
            switchIntent.putExtra("COURSE_DETAILS",myCourse);
            mCtx.startActivity(switchIntent);
        });

        itemHolder.deleteTerm.setOnClickListener(v->{

            new MaterialAlertDialogBuilder(mCtx).setTitle("DELETE COURSE")
                    .setMessage("Are you sure you want to delete this course?")
                    .setPositiveButton("YES", (dialog, which) -> {
                        dbHandler.deleteCourse((int )myCourse.getCourseId());
                        courses = new ArrayList<>();
                        Cursor myCursor = dbHandler.loadCourses();

                        if(myCursor.moveToFirst()){
                            do {
                                Course singleCourse = new Course();
                                singleCourse.setCourseId(myCursor.getInt(0));
                                singleCourse.setCourseTermID(myCursor.getInt(1));
                                singleCourse.setCourseDescription(myCursor.getString(2));
                                singleCourse.setCourseMentor(myCursor.getString(3));
                                singleCourse.setCourseMentorEmail(myCursor.getString(4));
                                singleCourse.setCourseMentorPhone(myCursor.getString(5));
                                singleCourse.setStatus(myCursor.getString(6));
                                singleCourse.setCourseTitle(myCursor.getString(7));
                                singleCourse.setCourseStartDate(myCursor.getString(8));
                                singleCourse.setCourseEndDate(myCursor.getString(9));

                                courses.add(singleCourse);
                            }while(myCursor.moveToNext());
                        }
                        myCursor.close();
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("NO",((dialog, which) -> {

                    }))
                    .create().show();


        });

        return convertView;
    }

    @Override
    public int getCount() {
        return courses.size();
    }
}
