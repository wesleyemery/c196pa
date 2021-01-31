package com.wgu_wemery.c196pa.adapters;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.wgu_wemery.c196pa.utils.DatabaseHandler;
import com.wgu_wemery.c196pa.R;
import com.wgu_wemery.c196pa.models.Term;

import java.util.ArrayList;
import java.util.Locale;

public class TermsAdapter extends ArrayAdapter<Term> {

    private ArrayList<Term> terms;
    private Context mCtx;
    private DatabaseHandler dbHandler;

    public TermsAdapter(@NonNull Context context, ArrayList<Term> terms, DatabaseHandler dbHandler) {
        super(context, R.layout.term_row,terms);
        this.terms = terms;
        this.mCtx = context;
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

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //retrieve position
        Term myTerm = terms.get(position);
        ItemViewHolder itemHolder;
        View output;

        if(convertView==null){

            itemHolder = new ItemViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            convertView = inflater.inflate(R.layout.term_row,parent,false);

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

        //set values
        itemHolder.name.setText("TERM NAME: "+myTerm.getTermTitle());
        itemHolder.startDate.setText("START DATE: "+myTerm.getTermStartDate());
        itemHolder.endDate.setText("END DATE: "+myTerm.getTermEndDate());
        View finalConvertView = convertView;
        itemHolder.deleteTerm.setOnClickListener(v->{

            new MaterialAlertDialogBuilder(mCtx)
                    .setTitle("DELETE: "+myTerm.getTermTitle())
                    .setIcon(R.drawable.ic_delete)
                    .setMessage("Are you sure you want to delete this course?")
                    .setPositiveButton("YES", (dialog, which) -> {
                        if(dbHandler.deleteTerm((int) myTerm.getTermId())){
                            Snackbar.make(finalConvertView,"DELETED SUCCESSFULLY",Snackbar.LENGTH_LONG).show();
                            loadList();
                        }else{
                            Snackbar.make(finalConvertView,"CAN NOT DELETE TERM\nTHERE ARE COURSES ATTACHED TO IT",Snackbar.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("NO", ((dialog, which) -> {}))
                    .create().show();


        });

        String status;
        if(myTerm.getCurrent()==1) status = "ACTIVE"; else status="INACTIVE";
        itemHolder.activeCourse.setText("TERM STATUS: "+ status);

        View finalConvertView1 = convertView;
        itemHolder.termCard.setOnClickListener(v->{
            View details = LayoutInflater.from(mCtx)
                    .inflate(R.layout.activity_term_detail,parent,false);
            TextView start,end,status_,creation,courses;
            start = details.findViewById(R.id.txt_detail_term_start);
            end = details.findViewById(R.id.txt_detail_term_end);
            status_ = details.findViewById(R.id.txt_detail_term_status);
            creation = details.findViewById(R.id.txt_detail_term_creation_date);
            courses = details.findViewById(R.id.txt_detail_term_course_count);
            //set texts

            start.setText("START DATE: "+myTerm.getTermStartDate());
            end.setText("END DATE: "+myTerm.getTermEndDate());
            status_.setText("COURSE STATUS: "+status);
            creation.setText("TERM CREATION DATE: "+myTerm.getCreationDate());
            courses.setText("NUMBER OF COURSES: "+myTerm.getCourseCount());
            //show dialog builder
            new MaterialAlertDialogBuilder(mCtx)
                    .setView(details)
                    .setTitle(myTerm.getTermTitle())
                    .setNegativeButton("CLOSE",((dialog, which) -> {
                    }))
                    .setPositiveButton("ADD COURSE",((dialog, which) -> {
                    addCourseLayout(parent,myTerm, finalConvertView1);
                    }))
                    .setCancelable(false)
                    .create().show();
        });

        return convertView;
    }

    private void loadList(){
        //call cursor from terms
        Cursor termCursor = dbHandler.loadTerms();
        if(termCursor.moveToFirst()){
            terms.clear();
            do {
                Term singleTerm = new Term();
                singleTerm.setTermId(termCursor.getInt(0));
                singleTerm.setTermTitle(termCursor.getString(1));
                singleTerm.setTermStartDate(termCursor.getString(2));
                singleTerm.setTermEndDate(termCursor.getString(3));
                if (termCursor.getString(4).equals("1")){
                    singleTerm.setCurrent(1);
                }
                singleTerm.setCreationDate(termCursor.getString(5));
                singleTerm.setCourseCount(dbHandler.courseCount((int) singleTerm.getTermId()));
                terms.add(singleTerm);
            }while(termCursor.moveToNext());
        }
        termCursor.close();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return terms.size();
    }

    @SuppressLint("SetTextI18n")
    private void addCourseLayout(final ViewGroup group, Term courseTerm,View convertView){
        View addCourseView = LayoutInflater.from(mCtx).inflate(R.layout.add_course_dialog,group,false);
        TextInputEditText title,instName,instEmail,instPhone,startDate,endDate,description;
        AppCompatSpinner statusSpinner;
        Button addCourseBtn;

        //initialize user interface
        title = addCourseView.findViewById(R.id.edt_new_course_title);
        instName = addCourseView.findViewById(R.id.edt_new_mentor_name);
        instEmail = addCourseView.findViewById(R.id.edt_new_mentor_email);
        instPhone = addCourseView.findViewById(R.id.edt_new_mentor_phone);
        startDate = addCourseView.findViewById(R.id.edt_new_course_start);
        endDate = addCourseView.findViewById(R.id.edt_new_course_end);
        description = addCourseView.findViewById(R.id.edt_new_course_description);
        statusSpinner = addCourseView.findViewById(R.id.spinnerStatusCourse);
        addCourseBtn = addCourseView.findViewById(R.id.btn_add_course);
        addCourseBtn.setText("ADD Course to "+courseTerm.getTermTitle());
        startDate.setText(currentDate());
        endDate.setText(currentDate());
        //create a dialog builder
        MaterialAlertDialogBuilder addCourseBuilder = new MaterialAlertDialogBuilder(mCtx)
                .setView(addCourseView)
                .setTitle("ADD COURSE")
                .setPositiveButton("CANCEL",((dialog, which) -> {}))
                .setCancelable(false);
        //Create dialog
        AlertDialog myDialog = addCourseBuilder.create();
        myDialog.show();

        //set dates
        startDate.setOnFocusChangeListener((v,hasFocus) -> {
            if(hasFocus)
                new DatePickerDialog(mCtx, dated(startDate), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        endDate.setOnFocusChangeListener((v,hasFocus) -> {
            if(hasFocus)
                new DatePickerDialog(mCtx, dated(endDate), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        addCourseBtn.setOnClickListener(v -> {

            if (title.getText().toString().isEmpty()){
                title.setError("Add a course title");
            }else{
                if(instName.getText().toString().isEmpty()){
                    instName.setError("Instructor Requires a name");
                }else{
                    if(instEmail.getText().length()<5){
                        instEmail.setError("Instructor needs a valid email");
                    }else{
                        if (instPhone.getText().length()<7){
                            instPhone.setError("Invalid phone number.");
                        }else{
                            if(description.getText().toString().isEmpty()){
                                description.setError("Give a valid description");
                            }else{

                                String courseTitle,instructorName,instructorEmail,instructorPhone,courseStart,courseEnd,courseDescription,courseStatus;
                                //initialize values
                                courseTitle = title.getText().toString();
                                instructorName = instName.getText().toString();
                                instructorEmail = instEmail.getText().toString();
                                instructorPhone = instPhone.getText().toString();
                                courseStart = startDate.getText().toString();
                                courseEnd = endDate.getText().toString();
                                courseDescription = description.getText().toString();

                                int status = statusSpinner.getSelectedItemPosition();
                                String[] statuses = mCtx.getResources().getStringArray(R.array.course_status);
                                courseStatus = statuses[status];

                                dbHandler.addCourse(courseTitle,
                                        courseStart,
                                        courseEnd,
                                        instructorName,
                                        instructorEmail,
                                        instructorPhone,
                                        courseDescription,
                                        (int) courseTerm.termId,
                                        courseStatus);

                                title.setText("");
                                instName.setText("");
                                instEmail.setText("");
                                instPhone.setText("");
                                description.setText("");

                                Snackbar.make(convertView,"ADDED COURSE SUCCESSFULLY",Snackbar.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                myDialog.dismiss();
                            }
                        }
                    }
                }
            }

        });

    }

    final Calendar myCalendar = Calendar.getInstance();

    private DatePickerDialog.OnDateSetListener dated(TextInputEditText inputEditText){
        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setCalendarDate(inputEditText);
        };
        return date;
    }

    private void setCalendarDate(TextInputEditText inputEditText){
        String myFormat = "dd-MMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        inputEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private String currentDate(){
        String myFormat = "dd-MMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        return sdf.format(myCalendar.getTime());
    }

}
