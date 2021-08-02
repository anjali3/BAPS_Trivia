package adhiveshan.bapsTrivia;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
//SJ-K2: AP
class TriviaQuizHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "DBquizz.db";

    //If you want to add more questions or wanna update table values
    //or any kind of modification in db just increment version no.
    private static final int DB_VERSION = 3;
    //Table name
    private static final String TABLE_NAME = "TQ";
    //Id of question
    private static final String UID = "_UID";
    //Question
    private static final String QUESTION = "QUESTION";
    //Option A
    private static final String OPTA = "OPTA";
    //Option B
    private static final String OPTB = "OPTB";
    //Option C
    private static final String OPTC = "OPTC";
    //Option D
    private static final String OPTD = "OPTD";
    //Answer
    private static final String ANSWER = "ANSWER";
    //So basically we are now creating table with first column-id , sec column-question , third column -option A, fourth column -option B , Fifth column -option C , sixth column -option D , seventh column - answer(i.e ans of  question)
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + UID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + QUESTION + " VARCHAR(255), " + OPTA + " VARCHAR(255), " + OPTB + " VARCHAR(255), " + OPTC + " VARCHAR(255), " + OPTD + " VARCHAR(255), " + ANSWER + " VARCHAR(255));";
    //Drop table query
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    TriviaQuizHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //OnCreate is called only once
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int j) {
        //OnUpgrade is called when ever we upgrade or increment our database version no
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    void allQuestion() {
        //temp list of trivia questions
        ArrayList<TriviaQuestion> arraylist = new ArrayList<>();

        arraylist.add(new TriviaQuestion("Who established BAPS?", "Acharya Raghuvirji Maharaj", "Gunatitanand Swami", "Shastriji Maharaj", "Shriji Maharaj", "Shastriji Maharaj"));

        arraylist.add(new TriviaQuestion("Which year was BAPS established?", "1905", "1907", "1917", "1913", "1907"));

        arraylist.add(new TriviaQuestion("Where was Shriji Maharaj born?", "Ayodhya", "Chapaiya", "Gadhada", "Mathura", "Chapaiya"));

        arraylist.add(new TriviaQuestion("Where was BAPS found?", "Gondal", "Bhuj", "Bochasan", "Vadhvan", "Bochasan"));

        arraylist.add(new TriviaQuestion("How old was Pramukh Swami Maharaj when he was appointed to be the President of BAPS?", "20", "26", "25", "28", "28"));

        arraylist.add(new TriviaQuestion("Which Gujarati calendar year was Pramukh Swami Maharaj appointed to be the President of BAPS", "Vaishakh sud 4, V.S. 2007", "Jeth sud 4, V.S. 2007", "Vaishakh sud 8, V.S. 2006", "Ashadh sud 5, V.S. 2008", "Vaishakh sud 4, V.S. 2007"));

        arraylist.add(new TriviaQuestion("How many paramhans did Shriji Maharaj have?", "200", "350", "400", "500", "500"));

        arraylist.add(new TriviaQuestion("Whose darbar did Maharaj stay in Gadhada?", "Jiva Khachar", "Vasta Khachar", "Dada Khachar", "Abhel Khachar", "Dada Khachar"));

        arraylist.add(new TriviaQuestion("In which samvat did Shriji Maharaj leave the Earth to go to his dham?", "Vaishakh sud 9", "Jeth sud 10", "Ashadh sud 9", "Fagan vad 11", "Jeth sud 10"));

        arraylist.add(new TriviaQuestion("How many total vachnamruts do we have?", "256", "271", "273", "281", "273"));

        arraylist.add(new TriviaQuestion("Where did Mahant Swami Maharaj receive bhagwati diksha?", "Gadhada", "Anand", "Gondal", "Junagadh", "Gadhada"));

        //More questions will be added for future
        this.addAllQuestions(arraylist);

    }


    private void addAllQuestions(ArrayList<TriviaQuestion> allQuestions) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (TriviaQuestion question : allQuestions) {
                values.put(QUESTION, question.getQuestion());
                values.put(OPTA, question.getOptA());
                values.put(OPTB, question.getOptB());
                values.put(OPTC, question.getOptC());
                values.put(OPTD, question.getOptD());
                values.put(ANSWER, question.getAnswer());
                db.insert(TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    List<TriviaQuestion> getAllOfTheQuestions() {

        List<TriviaQuestion> questionsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String column[] = {UID, QUESTION, OPTA, OPTB, OPTC, OPTD, ANSWER};
        Cursor cursor = db.query(TABLE_NAME, column, null, null, null, null, null);


        while (cursor.moveToNext()) {
            TriviaQuestion question = new TriviaQuestion();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1));
            question.setOptA(cursor.getString(2));
            question.setOptB(cursor.getString(3));
            question.setOptC(cursor.getString(4));
            question.setOptD(cursor.getString(5));
            question.setAnswer(cursor.getString(6));
            questionsList.add(question);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        cursor.close();
        db.close();
        return questionsList;
    }
}
