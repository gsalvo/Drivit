package cl.blackbirdhq.drivit;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cl.blackbirdhq.drivit.helpers.AdminSQLiteAPP;

public class PresentationClassC extends AppCompatActivity {
    private AdminSQLiteAPP data = new AdminSQLiteAPP(this);
    private SQLiteDatabase db;
    private ProgressDialog mDialog;
    private LoadQuestion loadQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation_class_c);
        mDialog = new ProgressDialog(this);
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                loadQuestion.cancel(true);
            }
        });
    }

    public void goTest(View view){
        loadQuestion = new LoadQuestion();
        loadQuestion.execute();
    }

    class LoadQuestion extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute(){
            mDialog.setMessage("Cargando las preguntas.");
            mDialog.setIndeterminate(false);
            mDialog.setCancelable(true);
            mDialog.show();
        }
        @Override
        protected String doInBackground(String... params){
            db = data.getWritableDatabase();
            data.reloadDBTest(db);
            db.execSQL("INSERT INTO categories(_id, name, special) values (1,'conocimientos legales y reglamentarias',0)");
            db.execSQL("INSERT INTO categories(_id, name, special) values (2,'conducta vial',0)");
            db.execSQL("INSERT INTO categories(_id, name, special) values (3,'conocimientos mecánica básica',0)");
            db.execSQL("INSERT INTO categories(_id, name, special) values (4,'seguridad vial',1)");

            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (1, 'Frente a una situación normal, ¿Cuál es la forma más segura de frenar?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (1, 'Frenando fuerte, poniendo la palanca de cambio en neutro y tirando el freno de mano justo antes de detenerse', 0, 1)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (2, 'Frenando suavemente, presionando el pedal de embrague y tirando el freno de mano justo antes de detenerse.', 0, 1)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (3, 'Frenando suavemente, luego un poco más fuerte cuando comienza a detenerse y después aflojando de a poco el freno antes de detenerse', 1, 1)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (2, '¿Cuándo puede usted hacer sonar la bocina de su auto?','','1')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (4, 'Para que le cedan el paso.', 0, 2)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (5, 'Para prevenir la ocurrencia de un accidente', 1, 2)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (6, 'Para llamar la atención de un amigo', 0, 2)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (3, '¿Cómo puede usted evitar que su vehículo patine cuando la calzada está cubierta con una capa de hielo?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (7, 'Usando el freno de mano si las ruedas comienzan a resbalar.', 0, 3)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (8, 'Conduciendo a una velocidad baja en el cambio más alto posible.', 1, 3)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (9, 'Conduciendo en un cambio bajo todo el tiempo.', 0, 3)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (4, '¿Cuándo usaría usted las luces de advertencia de peligro de su vehículo','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (10, 'Cuando esté retrocediendo en una calle de poco tránsito.', 0, 4)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (11, 'Cuando esté en pana y obstaculizando el tránsito.', 1, 4)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (12, 'Cuando esté en pana moviéndose lentamente', 0, 4)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (5, 'El grupo etario de mayor accidentabilidad son los jóvenes, entre:','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (13, '18 Y 29 AÑOS.', 1, 5)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (14, '30 Y 40 AÑOS.', 0, 5)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (15, '40 Y 49 AÑOS.', 0, 5)");

            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (6, 'Frente a una situación normal, ¿Cuál es la forma más segura de frenar?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (16, 'Frenando fuerte, poniendo la palanca de cambio en neutro y tirando el freno de mano justo antes de detenerse', 0, 6)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (17, 'Frenando suavemente, presionando el pedal de embrague y tirando el freno de mano justo antes de detenerse.', 0, 6)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (18, 'Frenando suavemente, luego un poco más fuerte cuando comienza a detenerse y después aflojando de a poco el freno antes de detenerse', 1, 6)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (7, '¿Cuándo puede usted hacer sonar la bocina de su auto?','','1')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (19, 'Para que le cedan el paso.', 0, 7)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (20, 'Para prevenir la ocurrencia de un accidente', 1, 7)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (21, 'Para llamar la atención de un amigo', 0, 7)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (8, '¿Cómo puede usted evitar que su vehículo patine cuando la calzada está cubierta con una capa de hielo?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (22, 'Usando el freno de mano si las ruedas comienzan a resbalar.', 0, 8)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (23, 'Conduciendo a una velocidad baja en el cambio más alto posible.', 1, 8)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (24, 'Conduciendo en un cambio bajo todo el tiempo.', 0, 8)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (9, '¿Cuándo usaría usted las luces de advertencia de peligro de su vehículo','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (25, 'Cuando esté retrocediendo en una calle de poco tránsito.', 0, 9)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (26, 'Cuando esté en pana y obstaculizando el tránsito.', 1, 9)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (27, 'Cuando esté en pana moviéndose lentamente', 0, 9)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (10, 'El grupo etario de mayor accidentabilidad son los jóvenes, entre:','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (28, '18 Y 29 AÑOS.', 1, 10)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (29, '30 Y 40 AÑOS.', 0, 10)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (30, '40 Y 49 AÑOS.', 0, 10)");

            /*db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (11, 'Frente a una situación normal, ¿Cuál es la forma más segura de frenar?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (31, 'Frenando fuerte, poniendo la palanca de cambio en neutro y tirando el freno de mano justo antes de detenerse', 0, 11)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (32, 'Frenando suavemente, presionando el pedal de embrague y tirando el freno de mano justo antes de detenerse.', 0, 11)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (33, 'Frenando suavemente, luego un poco más fuerte cuando comienza a detenerse y después aflojando de a poco el freno antes de detenerse', 1, 11)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (12, '¿Cuándo puede usted hacer sonar la bocina de su auto?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (34, 'Para que le cedan el paso.', 0, 12)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (35, 'Para prevenir la ocurrencia de un accidente', 1, 12)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (36, 'Para llamar la atención de un amigo', 0, 12)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (13, '¿Cómo puede usted evitar que su vehículo patine cuando la calzada está cubierta con una capa de hielo?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (37, 'Usando el freno de mano si las ruedas comienzan a resbalar.', 0, 13)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (38, 'Conduciendo a una velocidad baja en el cambio más alto posible.', 1, 13)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (39, 'Conduciendo en un cambio bajo todo el tiempo.', 0, 13)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (14, '¿Cuándo usaría usted las luces de advertencia de peligro de su vehículo','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (40, 'Cuando esté retrocediendo en una calle de poco tránsito.', 0, 14)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (41, 'Cuando esté en pana y obstaculizando el tránsito.', 1, 14)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (42, 'Cuando esté en pana moviéndose lentamente', 0, 14)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (15, 'El grupo etario de mayor accidentabilidad son los jóvenes, entre:','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (43, '18 Y 29 AÑOS.', 1, 15)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (44, '30 Y 40 AÑOS.', 0, 15)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (45, '40 Y 49 AÑOS.', 0, 15)");



            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (16, 'Frente a una situación normal, ¿Cuál es la forma más segura de frenar?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (61, 'Frenando fuerte, poniendo la palanca de cambio en neutro y tirando el freno de mano justo antes de detenerse', 0, 16)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (62, 'Frenando suavemente, presionando el pedal de embrague y tirando el freno de mano justo antes de detenerse.', 0, 16)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (63, 'Frenando suavemente, luego un poco más fuerte cuando comienza a detenerse y después aflojando de a poco el freno antes de detenerse', 1, 16)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (17, '¿Cuándo puede usted hacer sonar la bocina de su auto?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (64, 'Para que le cedan el paso.', 0, 17)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (65, 'Para prevenir la ocurrencia de un accidente', 1, 17)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (66, 'Para llamar la atención de un amigo', 0, 17)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (18, '¿Cómo puede usted evitar que su vehículo patine cuando la calzada está cubierta con una capa de hielo?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (67, 'Usando el freno de mano si las ruedas comienzan a resbalar.', 0, 18)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (68, 'Conduciendo a una velocidad baja en el cambio más alto posible.', 1, 18)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (69, 'Conduciendo en un cambio bajo todo el tiempo.', 0, 18)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (19, '¿Cuándo usaría usted las luces de advertencia de peligro de su vehículo','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (70, 'Cuando esté retrocediendo en una calle de poco tránsito.', 0, 19)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (71, 'Cuando esté en pana y obstaculizando el tránsito.', 1, 19)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (72, 'Cuando esté en pana moviéndose lentamente', 0, 19)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (20, 'El grupo etario de mayor accidentabilidad son los jóvenes, entre:','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (73, '18 Y 29 AÑOS.', 1, 20)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (74, '30 Y 40 AÑOS.', 0, 20)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (75, '40 Y 49 AÑOS.', 0, 20)");

            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (21, 'Frente a una situación normal, ¿Cuál es la forma más segura de frenar?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (76, 'Frenando fuerte, poniendo la palanca de cambio en neutro y tirando el freno de mano justo antes de detenerse', 0, 21)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (77, 'Frenando suavemente, presionando el pedal de embrague y tirando el freno de mano justo antes de detenerse.', 0, 21)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (78, 'Frenando suavemente, luego un poco más fuerte cuando comienza a detenerse y después aflojando de a poco el freno antes de detenerse', 1, 21)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (22, '¿Cuándo puede usted hacer sonar la bocina de su auto?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (79, 'Para que le cedan el paso.', 0, 22)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (80, 'Para prevenir la ocurrencia de un accidente', 1, 22)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (81, 'Para llamar la atención de un amigo', 0, 22)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (23, '¿Cómo puede usted evitar que su vehículo patine cuando la calzada está cubierta con una capa de hielo?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (82, 'Usando el freno de mano si las ruedas comienzan a resbalar.', 0, 23)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (83, 'Conduciendo a una velocidad baja en el cambio más alto posible.', 1, 23)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (84, 'Conduciendo en un cambio bajo todo el tiempo.', 0, 23)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (24, '¿Cuándo usaría usted las luces de advertencia de peligro de su vehículo','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (85, 'Cuando esté retrocediendo en una calle de poco tránsito.', 0, 24)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (86, 'Cuando esté en pana y obstaculizando el tránsito.', 1, 24)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (87, 'Cuando esté en pana moviéndose lentamente', 0, 24)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (25, 'El grupo etario de mayor accidentabilidad son los jóvenes, entre:','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (88, '18 Y 29 AÑOS.', 1, 25)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (89, '30 Y 40 AÑOS.', 0, 25)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (90, '40 Y 49 AÑOS.', 0, 25)");

            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (26, 'Frente a una situación normal, ¿Cuál es la forma más segura de frenar?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (91, 'Frenando fuerte, poniendo la palanca de cambio en neutro y tirando el freno de mano justo antes de detenerse', 0, 26)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (92, 'Frenando suavemente, presionando el pedal de embrague y tirando el freno de mano justo antes de detenerse.', 0, 26)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (93, 'Frenando suavemente, luego un poco más fuerte cuando comienza a detenerse y después aflojando de a poco el freno antes de detenerse', 1, 26)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (27, '¿Cuándo puede usted hacer sonar la bocina de su auto?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (94, 'Para que le cedan el paso.', 0, 27)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (95, 'Para prevenir la ocurrencia de un accidente', 1, 27)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (96, 'Para llamar la atención de un amigo', 0, 27)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (28, '¿Cómo puede usted evitar que su vehículo patine cuando la calzada está cubierta con una capa de hielo?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (97, 'Usando el freno de mano si las ruedas comienzan a resbalar.', 0, 28)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (98, 'Conduciendo a una velocidad baja en el cambio más alto posible.', 1, 28)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (99, 'Conduciendo en un cambio bajo todo el tiempo.', 0, 28)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (29, '¿Cuándo usaría usted las luces de advertencia de peligro de su vehículo','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (100, 'Cuando esté retrocediendo en una calle de poco tránsito.', 0, 29)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (101, 'Cuando esté en pana y obstaculizando el tránsito.', 1, 29)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (102, 'Cuando esté en pana moviéndose lentamente', 0, 29)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (30, 'El grupo etario de mayor accidentabilidad son los jóvenes, entre:','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (103, '18 Y 29 AÑOS.', 1, 30)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (104, '30 Y 40 AÑOS.', 0, 30)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (105, '40 Y 49 AÑOS.', 0, 30)");

            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (31, 'Frente a una situación normal, ¿Cuál es la forma más segura de frenar?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (106, 'Frenando fuerte, poniendo la palanca de cambio en neutro y tirando el freno de mano justo antes de detenerse', 0, 31)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (107, 'Frenando suavemente, presionando el pedal de embrague y tirando el freno de mano justo antes de detenerse.', 0, 31)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (108, 'Frenando suavemente, luego un poco más fuerte cuando comienza a detenerse y después aflojando de a poco el freno antes de detenerse', 1, 31)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (32, '¿Cuándo puede usted hacer sonar la bocina de su auto?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (109, 'Para que le cedan el paso.', 0, 32)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (110, 'Para prevenir la ocurrencia de un accidente', 1, 32)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (111, 'Para llamar la atención de un amigo', 0, 32)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (33, '¿Cómo puede usted evitar que su vehículo patine cuando la calzada está cubierta con una capa de hielo?','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (112, 'Usando el freno de mano si las ruedas comienzan a resbalar.', 0, 33)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (113, 'Conduciendo a una velocidad baja en el cambio más alto posible.', 1, 33)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (114, 'Conduciendo en un cambio bajo todo el tiempo.', 0, 33)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (34, '¿Cuándo usaría usted las luces de advertencia de peligro de su vehículo','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (115, 'Cuando esté retrocediendo en una calle de poco tránsito.', 0, 34)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (116, 'Cuando esté en pana y obstaculizando el tránsito.', 1, 34)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (117, 'Cuando esté en pana moviéndose lentamente', 0, 34)");
            db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (35, 'El grupo etario de mayor accidentabilidad son los jóvenes, entre:','','4')");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (118, '18 Y 29 AÑOS.', 1, 35)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (119, '30 Y 40 AÑOS.', 0, 35)");
            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (120, '40 Y 49 AÑOS.', 0, 35)");*/
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            Intent i = new Intent(PresentationClassC.this, Question.class);
            startActivity(i);
            mDialog.dismiss();
        }
    }
}
