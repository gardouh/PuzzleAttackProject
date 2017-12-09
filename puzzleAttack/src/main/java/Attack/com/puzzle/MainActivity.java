package Attack.com.puzzle;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// declaration de notre activity héritée de Activity
public class MainActivity extends Activity {
    Button play,pros,rep,music,save,load;
    static TextView time;
    private PuzzleView mSokobanView;
    int [][] matrice;
    TextView txt1,txt2;
    public boolean musicOn=true;
//    SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // initialise notre activity avec le constructeur parent    	
        super.onCreate(savedInstanceState);
        // charge le fichier main.xml comme vue de l'activité
        setContentView(R.layout.main);
        // recuperation de la vue une voie cree à partir de son id
        // rend visible la vue
        final MediaPlayer epic = MediaPlayer.create(this, R.raw.epic);
        time=(TextView) findViewById(R.id.time);

        play=(Button) findViewById(R.id.play);
        pros=(Button) findViewById(R.id.propos);
        rep=(Button) findViewById(R.id.restart);
        music=(Button) findViewById(R.id.epic);
        save=(Button) findViewById(R.id.save);
      //  load=(Button) findViewById(R.id.load);

        txt1=(TextView) findViewById(R.id.textView);
        txt2=(TextView) findViewById(R.id.textView2);
        mSokobanView = (PuzzleView)findViewById(R.id.SokobanView);

        mSokobanView.setVisibility(View.INVISIBLE);
//play
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epic.start();
                txt1.setText("");
                txt2.setText("");
                mSokobanView.setVisibility(View.VISIBLE);

            }
        });

//replay
        rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSokobanView.setVisibility(View.INVISIBLE);
                txt1.setText("");
                txt2.setText("");
                mSokobanView.initMatrice();
                mSokobanView.matrice[7][3]=2;
                mSokobanView.matrice[7][2]=2;
                mSokobanView.matrice[6][6]=2;
                mSokobanView.matrice[1][4]=1;
                mSokobanView.matrice[2][4]=1;
                mSokobanView.matrice[3][3]=1;
                mSokobanView.gravity();

                mSokobanView.setVisibility(View.VISIBLE);



            }
        });

        pros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt1.setText("Attack Puzzle");
                txt2.setText("Creer par Gardouh Khalil et Mohamed Nouh");

                mSokobanView.setVisibility(View.INVISIBLE);
            }
        });
//ouvrir et fermer la music
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt1.setText("");
                txt2.setText("");
                if (musicOn) {
                    epic.start();
                    musicOn=false;
                }else {
                    epic.pause();
                    musicOn=true;
                }
            }
        });
//save
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          //      SharedPreferences.Editor prefsEditor = mPrefs.edit();
         //       Gson gson = new Gson();
         //       String json = gson.toJson( mSokobanView.matrice);
        //        prefsEditor.putString("MyObject", json);
        //        prefsEditor.commit();

            }
        });

     //   load.setOnClickListener(new View.OnClickListener() {
       //     @Override
        //    public void onClick(View v) {
         //       Gson gson = new Gson();
         //       String json = mPrefs.getString("MyObject", "");
            //    int [] [] m = gson.fromJson(json);


      //      }
       // });




    }
}