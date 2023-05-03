package com.himan.to_do_list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editTask,updateId,deleteId;
    Button addBtn,updateBtn,deleteBtn,viewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb=new DatabaseHelper(this);

        editTask=findViewById(R.id.editTask);
        updateId=findViewById(R.id.updateId);
        deleteId=findViewById(R.id.deleteId);
        addBtn=findViewById(R.id.addBtn);
        updateBtn=findViewById(R.id.updateBtn);
        deleteBtn=findViewById(R.id.deleteBtn);
        viewBtn=findViewById(R.id.viewBtn);

        addTask();
        viewAll();
        updateTask();
        deleteTask();
    }

    public void addTask(){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editTask.getText().toString());
                if (isInserted)
                    Toast.makeText(MainActivity.this,"Task inserted successfully",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Task is not inserted",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewAll(){
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor results=myDb.getAllData();
                if (results.getCount()==0){
                    showMessage("Message:","No tasks in the list");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while (results.moveToNext()){
                    buffer.append("ID: "+results.getString(0)+"\n");
                    buffer.append("Task: "+results.getString(1)+"\n");

                    showMessage("TODO",buffer.toString());
                }
            }
        });
    }
    public void showMessage(String title,String message) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void updateTask(){
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate=myDb.UpdateData(updateId.getText().toString(),editTask.getText().toString());
                if(isUpdate)
                    Toast.makeText(MainActivity.this,"List Updated",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"List is not updated",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void deleteTask(){
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedatarows=myDb.DeleteData(deleteId.getText().toString());
                if(deletedatarows>0)
                    Toast.makeText(MainActivity.this,"Task deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Task is not deleted",Toast.LENGTH_LONG).show();
            }
        });
    }
}