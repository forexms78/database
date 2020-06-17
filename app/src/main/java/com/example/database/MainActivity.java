package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText snum, kor, eng, mat, snum1, name, age, sex;
    TextView text;
    Button ins, sel, com, del, one, upd, ins1, sel1;
    View.OnClickListener cl;
    String sql, res;
    MyHelper helper;
    SQLiteDatabase database;
    Cursor cur;

    class MyHelper extends SQLiteOpenHelper{
        MyHelper(Context c){
            super(c, "pupil",null,1);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table score (snum char(12), kor integer, eng integer, math integer, total integer, avg double);");
            db.execSQL("create table info (snum char(12), name char(20), age integer, sex char(8));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists score;");
            db.execSQL("drop table if exists info;");
            onCreate(db);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        snum = (EditText)findViewById(R.id.snumber);
        kor = (EditText)findViewById(R.id.korean);
        eng = (EditText)findViewById(R.id.english);
        mat = (EditText)findViewById(R.id.math);

        snum1 =(EditText)findViewById(R.id.snumber1);
        name =(EditText)findViewById(R.id.name);
        age =(EditText)findViewById(R.id.age);
        sex =(EditText)findViewById(R.id.sex);


        text = (TextView) findViewById(R.id.text);




        ins = (Button)findViewById(R.id.insert);
        sel = (Button)findViewById(R.id.select);
        com = (Button)findViewById(R.id.compute);
        del = (Button)findViewById(R.id.delete);
        one = (Button)findViewById(R.id.one);
        upd = (Button)findViewById(R.id.update);

        ins1 = (Button)findViewById(R.id.insert1);
        sel1 = (Button)findViewById(R.id.select1);




        helper = new MyHelper(this);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.insert:
                        sql = "insert into score values ('" + snum.getText().toString() + "'," + kor.getText().toString();
                        sql = sql + "," + eng.getText().toString() + "," + mat.getText().toString() + ",0,0.0);";
                        try {
                            database = helper.getWritableDatabase();
                            database.execSQL(sql);
                            database.close();
                            text.setText(sql);
                        } catch (Exception e){
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.select:
                        sql = "select * from score order by snum;";
                        try{
                            database = helper.getReadableDatabase();
                            cur = database.rawQuery(sql, null);
                            res = "";
                            while (cur.moveToNext()){
                                res = res + cur.getString(0) + "  " + cur.getString(1) + "  ";
                                res = res + cur.getString(2) + "  " + cur.getString(3) + "  ";
                                res = res + cur.getString(4) + "  " + cur.getString(5) + "\n";
                            }
                            text.setText(res);
                            database.close();
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.compute:
                        try{
                            sql ="update score set total = kor + eng + math;";
                            database = helper.getWritableDatabase();
                            database.execSQL(sql);
                            sql = "update score set avg = total / 3.0;";
                            database.execSQL(sql);
                            text.setText("update score set total = kor + eng + math;\n" + sql);
                            database.close();
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.delete:
                        sql = "delete from score where snum='" + snum.getText().toString() + "';";
                        try {
                            database = helper.getWritableDatabase();
                            database.execSQL(sql);
                            database.close();
                            text.setText(sql);
                        } catch (Exception e){
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.one:
                        sql = "select * from score where snum = '" + snum.getText().toString() + "';";
                        try{
                            database = helper.getReadableDatabase();
                            cur = database.rawQuery(sql, null);
                            if (cur.moveToNext()){
                                kor.setText(cur.getString(1));
                                eng.setText(cur.getString(2));
                                mat.setText(cur.getString(3));
                            }
                            text.setText(sql);
                            database.close();
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.update:
                        sql = "update score set kor=" + kor.getText().toString() + ", eng=" + eng.getText().toString();
                        sql = sql + ", math=" + mat.getText().toString() + " where snum='" + snum.getText().toString() + "';";
                        try {
                            database = helper.getWritableDatabase();
                            database.execSQL(sql);
                            String sql1;
                            sql1 = "update score set total = kor + eng + math where snum ='" + snum.getText().toString() + "';";
                            database = helper.getWritableDatabase();
                            database.execSQL(sql1);
                            sql1 = "update score set avg = total / 3.0 where snum='" + snum.getText().toString() + "';";
                            database.execSQL(sql1);
                            database.close();
                            text.setText(sql);
                        } catch (Exception e){
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.insert1:
                        sql = "insert into info values ('" + snum1.getText().toString() + "','" + name.getText().toString();
                        sql = sql + "'," + age.getText().toString() + ",'" + sex.getText().toString() + "');";
                        try {
                            database = helper.getWritableDatabase();
                            database.execSQL(sql);
                            database.close();
                            text.setText(sql);
                        } catch (Exception e){
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.select1:
                        sql = "select * from info order by snum;";
                        try{
                            database = helper.getReadableDatabase();
                            cur = database.rawQuery(sql, null);
                            res = "";
                            while (cur.moveToNext()){
                                res = res + cur.getString(0) + "  " + cur.getString(1) + "  ";
                                res = res + cur.getString(2) + "  " + cur.getString(3) + "\n";
                            }
                            text.setText(res);
                            database.close();
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        break;
                }
            }
        };
        ins.setOnClickListener(cl);
        sel.setOnClickListener(cl);
        com.setOnClickListener(cl);
        del.setOnClickListener(cl);
        one.setOnClickListener(cl);
        upd.setOnClickListener(cl);
        ins1.setOnClickListener(cl);
        sel1.setOnClickListener(cl);
    }
}
